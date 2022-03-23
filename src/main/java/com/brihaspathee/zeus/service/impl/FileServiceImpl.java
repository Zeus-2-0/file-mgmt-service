package com.brihaspathee.zeus.service.impl;

import com.brihaspathee.zeus.producer.FileInfoProducer;
import com.brihaspathee.zeus.service.interfaces.FileService;
import com.brihaspathee.zeus.web.model.FileDetailDto;
import com.brihaspathee.zeus.web.model.TradingPartnerDto;
import com.brihaspathee.zeus.web.response.ZeusApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneId;

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

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    private final FileInfoProducer fileInfoProducer;

    @Override
    public void processFile(Resource resource) throws IOException {
        BasicFileAttributes basicAttributes = Files.readAttributes(resource.getFile().toPath(), BasicFileAttributes.class);
        FileTime fileTime = basicAttributes.creationTime();
        LocalDateTime fileCreationTime = LocalDateTime.ofInstant(fileTime.toInstant(), ZoneId.systemDefault());
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
        log.info("File Creation Time:{}", fileCreationTime);
        ResponseEntity<ZeusApiResponse> response =
                restTemplate.getForEntity("http://localhost:8081/api/v1/tp/" + senderId + "/" + receiverId, ZeusApiResponse.class);
        ZeusApiResponse apiResponse = response.getBody();
        TradingPartnerDto tradingPartnerDto =
                objectMapper.convertValue(apiResponse.getResponse(), TradingPartnerDto.class);
        log.info("Trading Partner Info:{}", tradingPartnerDto);

        FileDetailDto fileDetailDto = FileDetailDto.builder()
                .fileId("Test File Id")
                .fileName(fileName)
                .fileReceivedDate(fileCreationTime)
                .tradingPartnerId(tradingPartnerDto.getTradingPartnerId())
                .lineOfBusinessTypeCode(tradingPartnerDto.getLineOfBusinessTypeCode())
                .senderId(tradingPartnerDto.getSenderId())
                .receiverId(tradingPartnerDto.getReceiverId())
                .marketplaceTypeCode(tradingPartnerDto.getMarketplaceTypeCode())
                .fileData(fileData)
                .build();
        fileInfoProducer.sendFileInfo(fileDetailDto);
    }
}
