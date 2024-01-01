package com.brihaspathee.zeus.integration;

import com.brihaspathee.zeus.service.interfaces.FileService;
import com.brihaspathee.zeus.test.BuildTestData;
import com.brihaspathee.zeus.test.TestClass;
import com.brihaspathee.zeus.test.ZeusTransactionControlNumber;
import com.brihaspathee.zeus.web.model.EDITransactionProcessingRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 25, December 2023
 * Time: 9:42 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.integration
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EDITransactionProcessingIntTest {

    /**
     * The spring environment instance
     */
    @Autowired
    private Environment environment;

    /**
     * Object mapper to read the file and convert it to an object
     */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Rest template to call the api endpoint
     */
    @Autowired
    private TestRestTemplate testRestTemplate;

    /**
     * The file that contains the test data
     */
    @Value("classpath:com/brihaspathee/zeus/integration/EDITransactionProcessingIntTest.json")
    Resource resourceFile;

    /**
     * The instance of the class that helps to build the input data
     */
    private TestClass<EDITransactionProcessingRequest> ediTransactionProcessingRequestTestClass;

    /**
     * The instance of the class that helps to build the data
     */
    @Autowired
    private BuildTestData<EDITransactionProcessingRequest> buildTestData;

    /**
     * The list of test requests
     */
    private List<EDITransactionProcessingRequest> requests = new ArrayList<>();

    /**
     * This is the property in application.yaml that
     * contains the location of the files
     */
    @Value("${edi.file.location}")
    private String ediFileLocation;

    /**
     * This is the property in application.yaml that
     * contains the location where to archive the files
     */
    @Value("${edi.file.archive-location}")
    private String ediFileArchiveLocation;

    /**
     * Spring Application Context
     */
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * Instance of file service to process the file
     */
    @Autowired
    private FileService fileService;

    /**
     * The setup method is executed before each test method is executed
     * @param testInfo
     * @throws IOException
     */
    @BeforeEach
    void setUp(TestInfo testInfo) throws IOException {

        // Read the file information and convert to test class object
        ediTransactionProcessingRequestTestClass = objectMapper.readValue(resourceFile.getFile(), new TypeReference<TestClass<EDITransactionProcessingRequest>>() {});

        // Build the test data for the test method that is to be executed
        this.requests = buildTestData.buildData(testInfo.getTestMethod().get().getName(),this.ediTransactionProcessingRequestTestClass);
    }

    /**
     * This method tests the processing of the transaction
     * @param repetitionInfo the repetition identifies the test iteration
     */
    @RepeatedTest(1)
    @Order(1)
    void testProcessTransaction(RepetitionInfo repetitionInfo) throws IOException {
        if(Arrays.asList(environment.getActiveProfiles()).contains("int-test")){
            // Retrieve the accounting processing request for the repetition
            EDITransactionProcessingRequest processingRequest = requests.get(repetitionInfo.getCurrentRepetition() - 1);
            log.info("Processing Request:{}", processingRequest);

            String ediFile = ediFileLocation + processingRequest.getFileName();
            log.info("Edi File Location:{}", ediFileLocation + ediFile);
            List<ZeusTransactionControlNumber> testTransactionControlNumbers = processingRequest.getTransactionControlNumbers();
            log.info("Transaction Control Numbers:{}", testTransactionControlNumbers);
            Resource [] resources = applicationContext.getResources("file:"+ediFile);
            for(Resource resource: resources){
                fileService.processFile(resource, testTransactionControlNumbers);
            }
        }else{
            log.info("Environment is not integration testing, hence not running the test");
        }

    }
}
