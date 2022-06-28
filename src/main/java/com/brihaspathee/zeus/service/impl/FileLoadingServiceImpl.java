package com.brihaspathee.zeus.service.impl;

import com.brihaspathee.zeus.service.interfaces.FileLoadingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.util.Arrays;

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

    @Value("${edi.file.archive-location}")
    private String ediFileArchiveLocation;

    @Override
    public Resource[] loadEDIFiles() throws Exception {
        log.info("File Location:{}", ediFileLocation);
        Resource [] resource = applicationContext.getResources("file:"+ediFileLocation);
        return resource   ;
    }

    @Override
    public void archiveFiles(Resource[] resources) {
        Arrays.stream(resources).forEach(resource -> {
            try {
                File sourceFile = resource.getFile();
                File destination = new File(ediFileArchiveLocation+"/"+resource.getFilename());
                copy(sourceFile, destination);
                sourceFile.delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void copy(File source, File destination) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(destination);
            // buffer size 1K
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = is.read(buf)) > 0) {
                os.write(buf, 0, bytesRead);
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally { is.close(); os.close(); }

    }
}
