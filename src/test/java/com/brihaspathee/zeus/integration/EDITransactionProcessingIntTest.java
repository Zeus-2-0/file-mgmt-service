package com.brihaspathee.zeus.integration;

import com.brihaspathee.zeus.constants.ZeusServiceNames;
import com.brihaspathee.zeus.dto.account.AccountDto;
import com.brihaspathee.zeus.dto.account.AccountList;
import com.brihaspathee.zeus.dto.account.PremiumPaymentDto;
import com.brihaspathee.zeus.dto.transaction.TransactionDto;
import com.brihaspathee.zeus.service.interfaces.FileService;
import com.brihaspathee.zeus.test.BuildTestData;
import com.brihaspathee.zeus.test.TestClass;
import com.brihaspathee.zeus.test.ZeusTransactionControlNumber;
import com.brihaspathee.zeus.test.validator.AccountValidator;
import com.brihaspathee.zeus.test.validator.TransactionValidator;
import com.brihaspathee.zeus.web.model.EDIExpectedOutput;
import com.brihaspathee.zeus.web.model.EDITransactionProcessingRequest;
import com.brihaspathee.zeus.web.model.ExpectedAccount;
import com.brihaspathee.zeus.web.response.ZeusApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.http.*;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

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
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EDITransactionProcessingIntTest {

    /**
     * The host for the transaction manager
     */
    @Value("${url.host.transaction-manager}")
    private String transactionManagerHost;

    /**
     * The host for the member management service
     */
    @Value("${url.host.member-management-service}")
    private String mmsHost;

    /**
     * The host for the premium billing service
     */
    @Value("${url.host.premium-billing-service}")
    private String pbHost;

    /**
     * The spring environment instance
     */
    private static Environment environment;

    /**
     * Object mapper to read the file and convert it to an object
     */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Rest template to call the api endpoint
     */
    private static TestRestTemplate testRestTemplate;

    /**
     * The account validation instance to validate the details of the account
     */
    private final AccountValidator accountValidator = new AccountValidator();

    /**
     * The account validation instance to validate the details of the account
     */
    private final TransactionValidator transactionValidator = new TransactionValidator();

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
    private static List<EDITransactionProcessingRequest> requests = new ArrayList<>();

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

    @Autowired
    private void init(Environment environment, TestRestTemplate testRestTemplate) {
        EDITransactionProcessingIntTest.environment = environment;
        EDITransactionProcessingIntTest.testRestTemplate = testRestTemplate;
    }

    /**
     * The setup method is executed before each test method is executed
     * @param testInfo
     * @throws IOException
     */
    @BeforeEach
    void setUp(TestInfo testInfo) throws IOException {
        log.info("Inside setup");

        // Read the file information and convert to test class object
        ediTransactionProcessingRequestTestClass = objectMapper.readValue(resourceFile.getFile(), new TypeReference<TestClass<EDITransactionProcessingRequest>>() {});

        accountValidator.setTestServiceName(ZeusServiceNames.FILE_MGMT_SERVICE);

        // Build the test data for the test method that is to be executed
        requests = buildTestData.buildData(testInfo.getTestMethod().get().getName(),this.ediTransactionProcessingRequestTestClass);
    }

    @AfterAll
    static void cleanUp(){

        if(Arrays.asList(environment.getActiveProfiles()).contains("int-test")){
            log.info("Inside Cleanup");
            // Clean up the data in file storage service
            cleanUpFileStorageService();
            // Clean up the data in transaction origination service
            cleanUpTransactionOriginationService();
            // Clean up the data in transaction storage service
            cleanUpTransactionStorageService();
            // Clean up the data in data transformation service
            cleanUpDataTransformerService();
            // Clean up the data in transaction manager service
            cleanUpTransactionManagerService();
            // Clean up the data in validation service
            cleanUpValidationService();
            // Clean up the data in account processor service
            cleanUpAccountProcessorService();
            // Clean up the data in member management service
            cleanUpMemberManagementService();
            // Clean up the data in premium billing service
            cleanUpPremiumBillingService();
        }

    }

    /**
     * This method tests the processing of the transaction
     * @param repetitionInfo the repetition identifies the test iteration
     */
    @RepeatedTest(67) // total - 64
    @Order(1)
    void testProcessTransaction(RepetitionInfo repetitionInfo) throws IOException, InterruptedException {
        if(Arrays.asList(environment.getActiveProfiles()).contains("int-test")){
            if(repetitionInfo.getCurrentRepetition() >= 65){
                testTransactions(repetitionInfo);
            }
//            testTransactions(repetitionInfo);
        }else{
            log.info("Environment is not integration testing, hence not running the test");
        }
    }

    /**
     * Test the transactions
     * @param repetitionInfo
     * @throws IOException
     * @throws InterruptedException
     */
    private void testTransactions(RepetitionInfo repetitionInfo) throws IOException, InterruptedException {
        // Retrieve the accounting processing request for the repetition
        EDITransactionProcessingRequest processingRequest = requests.get(repetitionInfo.getCurrentRepetition() - 1);
        log.info("Processing Request:{}", processingRequest);

        String ediFile = ediFileLocation + processingRequest.getFolderName() + "/" + processingRequest.getFileName();
        log.info("Edi File to be tested:{}", ediFile);
        List<ZeusTransactionControlNumber> testTransactionControlNumbers = processingRequest.getTransactionControlNumbers();
//            log.info("Transaction Control Numbers:{}", testTransactionControlNumbers);
        Resource [] resources = applicationContext.getResources("file:"+ediFile);
        for(Resource resource: resources){
            String fileName = resource.getFilename();
            log.info("File Name to be tested:{}", fileName);
            fileService.processFile(resource, testTransactionControlNumbers);
            // Make the thread to sleep to let the transaction processing
            // to complete before it can be validated
            Thread.sleep(Duration.ofSeconds(30));
        }

        // Validate the transaction and account once the transaction processing is completed
        Map<String, EDIExpectedOutput> expectedOutputMap = processingRequest.getExpectedOutput();
        testTransactionControlNumbers.forEach(zeusTransactionControlNumber -> {
            String transactionControlNumber = zeusTransactionControlNumber.getTcn();
            EDIExpectedOutput ediExpectedOutput = expectedOutputMap.get(transactionControlNumber);
            log.info("Expected outputs for transaction control number:{}", transactionControlNumber);
            TransactionDto expectedTransaction = ediExpectedOutput.getExpectedTransactionDto();
            TransactionDto actualTransaction = getTransactionByZtcn(zeusTransactionControlNumber.getZtcn());
            transactionValidator.assertTransaction(expectedTransaction, actualTransaction);
            AccountDto expectedAccount = ediExpectedOutput.getExpectedAccountDto();
            AccountDto actualAccount = getAccountByAccountNumber(ediExpectedOutput.getExpectedAccountDto().getAccountNumber());
            assertAccount(expectedAccount, actualAccount);
            AccountDto expectedPBAccount = ediExpectedOutput.getExpectedPBAccountDto();
            AccountDto actualPBAccount = getAccountFromPB(ediExpectedOutput.getExpectedAccountDto().getAccountNumber());
            assertAccount(expectedPBAccount, actualPBAccount);
            // Now that the transaction processing is completed
            // post premium payments if any present in the test case
            // and validate the changes in the MMS and Premium Billing
            log.info("transaction control number before posting payments:{}", zeusTransactionControlNumber);
            postPaymentsAndAssert(zeusTransactionControlNumber, ediExpectedOutput);
        });
    }

    private void postPaymentsAndAssert(ZeusTransactionControlNumber zeusTransactionControlNumber, EDIExpectedOutput ediExpectedOutput) {
        log.info("transaction control number:{}", zeusTransactionControlNumber);
        List<PremiumPaymentDto> premiumPaymentDtos = zeusTransactionControlNumber.getPremiumPayments();
        log.info("premium payments:{}", premiumPaymentDtos);
        Map<LocalDate, ExpectedAccount> expectedAccountMap = ediExpectedOutput.getExpectedAccounts();
        log.info("Expected Accounts Map: {}", expectedAccountMap);
        if(premiumPaymentDtos != null){
            premiumPaymentDtos.forEach(premiumPaymentDto -> {
                postPremiumPayment(premiumPaymentDto);
                try {
                    Thread.sleep(Duration.ofSeconds(10));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                ExpectedAccount expectedAccountPostPayment =
                        expectedAccountMap.get(premiumPaymentDto.getPaymentDate());
                AccountDto expectedMMSAccountPostPayment = expectedAccountPostPayment.getExpectedAccountDto();
                AccountDto actualMMSAccountPostPayment = getAccountByAccountNumber(
                        ediExpectedOutput.getExpectedAccountDto().getAccountNumber());
                assertAccount(expectedMMSAccountPostPayment, actualMMSAccountPostPayment);
                AccountDto expectedPBAccountPostPayment = expectedAccountPostPayment.getExpectedPBAccountDto();
                AccountDto actualPBAccountPostPayment = getAccountFromPB(ediExpectedOutput.getExpectedAccountDto().getAccountNumber());
                assertAccount(expectedPBAccountPostPayment, actualPBAccountPostPayment);
            });
        }
    }

    private void assertAccount(AccountDto expectedAccount, AccountDto actualAccount) {
        try {
            accountValidator.assertAccount(expectedAccount, actualAccount);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get transaction by ZTCN
     * @param ztcn
     * @return
     */
    private TransactionDto getTransactionByZtcn(String ztcn){
        log.info("Transaction Manager Host:{}", transactionManagerHost);
        String uri = transactionManagerHost+"/api/v1/zeus/transaction/"+ztcn;
        log.info("URI:{}", uri);
        ResponseEntity<ZeusApiResponse> transactionResponseEntity  =
                testRestTemplate.getForEntity(
                        uri,
                        ZeusApiResponse.class);
        // Get the response body from the response
        ZeusApiResponse transactionResponse = transactionResponseEntity.getBody();
        // get the transaction response
        assert transactionResponse != null;
        TransactionDto transactionDto =
                objectMapper.convertValue(transactionResponse.getResponse(), TransactionDto.class);
        log.info("Actual Transaction Dto:{}", transactionDto);
        return transactionDto;
    }

    /**
     * Get the account dto using the account number
     * @param accountNumber
     * @return
     */
    private AccountDto getAccountByAccountNumber(String accountNumber){
        log.info("Member Management Service Host:{}", mmsHost);
        String uri = mmsHost+"/api/v1/zeus/account/"+accountNumber;
        log.info("URI:{}", uri);
        ResponseEntity<ZeusApiResponse> updatedAccountResponseEntity  =
                testRestTemplate.getForEntity(
                        uri,
                        ZeusApiResponse.class);
        // Get the response body from the response
        ZeusApiResponse updatedAccountResponse = updatedAccountResponseEntity.getBody();
        // get the list of accounts that matched
        assert updatedAccountResponse != null;
        AccountList accountList =
                objectMapper.convertValue(updatedAccountResponse.getResponse(), AccountList.class);
        assertNotNull(accountList);
        assertTrue(accountList.getAccountDtos().stream().findFirst().isPresent());
        AccountDto accountDto = accountList.getAccountDtos().stream().findFirst().get();
        log.info("Actual Account Dto:{}", accountDto);
        return accountDto;
    }

    /**
     * Get the account that was created in premium billing
     * @param accountNumber
     * @return
     */
    private AccountDto getAccountFromPB(String accountNumber){
        log.info("Premium Billing Service Host:{}", pbHost);
        String uri = pbHost+"/api/v1/zeus/premium-billing/"+accountNumber;
        log.info("URI:{}", uri);
        ResponseEntity<ZeusApiResponse> updatedAccountResponseEntity  =
                testRestTemplate.getForEntity(
                        uri,
                        ZeusApiResponse.class);
        // Get the response body from the response
        ZeusApiResponse updatedAccountResponse = updatedAccountResponseEntity.getBody();
        // get the list of accounts that matched
        assert updatedAccountResponse != null;
        AccountList accountList =
                objectMapper.convertValue(updatedAccountResponse.getResponse(), AccountList.class);
        assertNotNull(accountList);
        assertTrue(accountList.getAccountDtos().stream().findFirst().isPresent());
        AccountDto accountDto = accountList.getAccountDtos().stream().findFirst().get();
        log.info("Actual Account Dto from premium billing:{}", accountDto);
        return accountDto;
    }

    /**
     * Post premium payments
     * @param premiumPaymentDto
     */
    private void postPremiumPayment(PremiumPaymentDto premiumPaymentDto){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PremiumPaymentDto> httpEntity = new HttpEntity<>(premiumPaymentDto, headers);
        log.info("Premium Billing Service Host:{}", pbHost);
        String uri = pbHost+"/api/v1/zeus/premium-billing/payment";
        log.info("URI:{}", uri);
        // Call the API Endpoint to process the premium payment
        testRestTemplate
                .postForEntity(uri,httpEntity, ZeusApiResponse.class);
    }

    /**
     * Clean up data in file storage service
     */
    private static void cleanUpFileStorageService(){
        HttpEntity<String> entity = new HttpEntity<>(null);
        String url = "http://localhost:8083/api/v1/file-storage/delete";
        testRestTemplate.exchange(url, HttpMethod.DELETE, entity, ZeusApiResponse.class);
        log.info("File Storage Service db cleaned up.");
    }

    /**
     * Clean up the data in transaction origination service
     */
    private static void cleanUpTransactionOriginationService(){
        HttpEntity<String> entity = new HttpEntity<>(null);
        String url = "http://localhost:8085/api/v1/trans-orig/delete";
        testRestTemplate.exchange(url, HttpMethod.DELETE, entity, ZeusApiResponse.class);
        log.info("Transaction Origination Service db cleaned up.");
    }

    /**
     * Clean up the data in transaction storage service
     */
    private static void cleanUpTransactionStorageService(){
        HttpEntity<String> entity = new HttpEntity<>(null);
        String url = "http://localhost:8087/api/v1/transaction/storage/delete";
        testRestTemplate.exchange(url, HttpMethod.DELETE, entity, ZeusApiResponse.class);
        log.info("Transaction Storage Service db cleaned up.");
    }

    /**
     * Clean up the data in data transformation service
     */
    private static void cleanUpDataTransformerService(){
        HttpEntity<String> entity = new HttpEntity<>(null);
        String url = "http://localhost:8096/api/v1/data-transform/delete";
        testRestTemplate.exchange(url, HttpMethod.DELETE, entity, ZeusApiResponse.class);
        log.info("Data Transformer Service db cleaned up.");
    }

    /**
     * Delete the data from Transaction Manager service
     */
    private static void cleanUpTransactionManagerService(){
        HttpEntity<String> entity = new HttpEntity<>(null);
        String url = "http://localhost:8095/api/v1/zeus/transaction/delete";
        testRestTemplate.exchange(url, HttpMethod.DELETE, entity, ZeusApiResponse.class);
        log.info("Transaction Manager Service db cleaned up.");
    }

    /**
     * Delete the data from Validation service
     */
    private static void cleanUpValidationService(){
        HttpEntity<String> entity = new HttpEntity<>(null);
        String url = "http://localhost:8089/api/v1/zeus/validation/delete";
        testRestTemplate.exchange(url, HttpMethod.DELETE, entity, ZeusApiResponse.class);
        log.info("Validation Service db cleaned up.");
    }

    /**
     * Delete the data from Account Processing service
     */
    private static void cleanUpAccountProcessorService(){
        HttpEntity<String> entity = new HttpEntity<>(null);
        String url = "http://localhost:8099/api/v1/zeus/account-processor/delete";
        testRestTemplate.exchange(url, HttpMethod.DELETE, entity, ZeusApiResponse.class);
        log.info("Account Processor Service db cleaned up.");
    }

    /**
     * Delete the data from Member Management service
     */
    private static void cleanUpMemberManagementService(){
        HttpEntity<String> entity = new HttpEntity<>(null);
        String url = "http://localhost:8084/api/v1/zeus/account/delete";
        testRestTemplate.exchange(url, HttpMethod.DELETE, entity, ZeusApiResponse.class);
        log.info("Member Management Service db cleaned up.");
    }

    /**
     * Delete the data from Premium Billing service
     */
    private static void cleanUpPremiumBillingService(){
        HttpEntity<String> entity = new HttpEntity<>(null);
        String url = "http://localhost:9004/api/v1/zeus/premium-billing/delete";
        testRestTemplate.exchange(url, HttpMethod.DELETE, entity, ZeusApiResponse.class);
        log.info("Premium Billing Service db cleaned up.");
    }
}
