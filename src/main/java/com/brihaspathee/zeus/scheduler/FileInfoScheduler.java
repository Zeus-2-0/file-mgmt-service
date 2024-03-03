package com.brihaspathee.zeus.scheduler;

import com.brihaspathee.zeus.service.interfaces.FileLoadingService;
import com.brihaspathee.zeus.service.interfaces.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;

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

    /**
     * File Loading Service instance
     */
    private final FileLoadingService fileLoadingService;

    /**
     * File Service instance
     */
    private final FileService fileService;

    /**
     * The spring environment instance
     */
    private final Environment environment;

    /**
     * Process EDI files
     * @throws Exception
     */
    @Scheduled(fixedRate = 60000, initialDelay = 60000)
    public void processFile() throws Exception {
        if(!Arrays.asList(environment.getActiveProfiles()).contains("int-test")){
            log.info("Scheduler running");
            Resource[] resources = fileLoadingService.loadEDIFiles();
            log.info("No. of Files:{}", resources.length);
            for(Resource resource: resources){
                fileService.processFile(resource, null);
            }
            fileLoadingService.archiveFiles(resources);
        }
    }
}
