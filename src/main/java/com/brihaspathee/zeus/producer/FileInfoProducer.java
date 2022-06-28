package com.brihaspathee.zeus.producer;

import com.brihaspathee.zeus.message.MessageMetadata;
import com.brihaspathee.zeus.message.ZeusMessagePayload;
import com.brihaspathee.zeus.web.model.FileDetailDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 15, March 2022
 * Time: 2:48 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.producer
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FileInfoProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper;

    public void sendFileInfo(FileDetailDto fileDetailDto) throws JsonProcessingException {
        log.info("About to send the file information");
        ZeusMessagePayload<FileDetailDto> filePayload = ZeusMessagePayload.<FileDetailDto>builder()
                .payload(fileDetailDto)
                .messageMetadata(MessageMetadata.builder()
                        .messageSource("FILE-MGMT")
                        .messageDestination(new String[] {"FILE-STORE", "TRANS-ORIG"})
                        .build())
                .build();
        //fileDetailDto.setFileData("Test File Data");
        //String fileInfoAsString = objectMapper.writeValueAsString(fileDetailDto);
        kafkaTemplate.send("ZEUS.FILE.STORAGE.TOPIC", "Test String");
        //kafkaTemplate.send("ZEUS.TRANS.ORIG.TOPIC", "Test String");

    }

}
