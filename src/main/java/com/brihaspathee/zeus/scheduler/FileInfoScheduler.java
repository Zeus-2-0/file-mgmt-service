package com.brihaspathee.zeus.scheduler;

import com.brihaspathee.zeus.producer.FileInfoProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 15, March 2022
 * Time: 3:01 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.scheduler
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FileInfoScheduler {

    private final FileInfoProducer fileInfoProducer;

    //@Scheduled(fixedRate = 1000)
    public void sendFileInfoMessage() throws JsonProcessingException {
        log.info("Scheduler running");
        /*FileInfo fileInfo = FileInfo.builder()
                .fileId("Test")
                .fileName("Test File")
                .fileDetail("Test Detail")
                .build();
        fileInfoProducer.sendFileInfo(fileInfo);*/
    }
}
