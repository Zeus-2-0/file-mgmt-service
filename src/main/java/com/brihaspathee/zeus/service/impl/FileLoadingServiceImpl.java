package com.brihaspathee.zeus.service.impl;

import com.brihaspathee.zeus.service.interfaces.FileLoadingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 21, March 2022
 * Time: 1:30 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.service.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FileLoadingServiceImpl implements FileLoadingService {

    /**
     * Spring Application Context
     */
    private final ApplicationContext applicationContext;

    /**
     * This is the property in application.yaml that
     * contains the location of the files
     */
    @Value("${edi.file.location}")
    private String ediFileLocation;

    @Override
    public Resource[] loadEDIFiles() throws Exception {
        log.info("File Location:{}", ediFileLocation);
        Resource [] resource = applicationContext.getResources("file:"+ediFileLocation);
        return resource   ;
    }
}
