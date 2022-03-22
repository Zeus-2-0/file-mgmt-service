package com.brihaspathee.zeus.bootstrap;

import com.brihaspathee.zeus.service.interfaces.FileLoadingService;
import com.brihaspathee.zeus.service.interfaces.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 21, March 2022
 * Time: 1:28 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.bootstrap
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LoadEDIFile implements CommandLineRunner {

    private final FileLoadingService fileLoadingService;

    private final FileService fileService;


    @Override
    public void run(String... args) throws Exception {
        Resource[] resources = fileLoadingService.loadEDIFiles();
        log.info("No. of Files:{}", resources.length);
        for(Resource resource: resources){
            fileService.processFile(resource);
        }
    }
}
