package com.brihaspathee.zeus.scheduler;

import com.brihaspathee.zeus.service.interfaces.FileLoadingService;
import com.brihaspathee.zeus.service.interfaces.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Scheduled;
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

    private final FileLoadingService fileLoadingService;

    private final FileService fileService;

    @Scheduled(fixedRate = 60000, initialDelay = 60000)
    public void processFile() throws Exception {
        log.info("Scheduler running");
        Resource[] resources = fileLoadingService.loadEDIFiles();
        log.info("No. of Files:{}", resources.length);
        for(Resource resource: resources){
            fileService.processFile(resource);
        }
        fileLoadingService.archiveFiles(resources);

    }
}
