package com.brihaspathee.zeus.service.impl;

import com.brihaspathee.zeus.service.interfaces.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 21, March 2022
 * Time: 3:16 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.service.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    @Override
    public void processFile(Resource resource) throws IOException {
        BasicFileAttributes basicAttributes = Files.readAttributes(resource.getFile().toPath(), BasicFileAttributes.class);
        FileTime fileTime = basicAttributes.creationTime();
        String fileName = resource.getFilename();
        InputStream inputStream = resource.getInputStream();
        byte[] fileBinaryData = FileCopyUtils.copyToByteArray(inputStream);
        String fileData = new String(fileBinaryData, StandardCharsets.UTF_8);
        fileData = fileData.replaceAll("\\r\\n|\\r|\\n", "");
        String interchangeSegment = fileData.split("~")[0];
        String senderId = interchangeSegment.split("\\*")[6].trim();
        String receiverId = interchangeSegment.split("\\*")[8].trim();
        log.info("File Name:{}", fileName);
        log.info("File Data:{}", fileData);
        log.info("Interchange Segment:{}", interchangeSegment);
        log.info("Sender Id:{}", senderId);
        log.info("Receiver Id:{}", receiverId);
    }
}
