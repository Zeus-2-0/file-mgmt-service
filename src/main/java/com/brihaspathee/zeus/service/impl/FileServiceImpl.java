package com.brihaspathee.zeus.service.impl;

import com.brihaspathee.zeus.domain.entity.FileDetail;
import com.brihaspathee.zeus.domain.repository.FileDetailRepository;
import com.brihaspathee.zeus.dto.transaction.FileDetailDto;
import com.brihaspathee.zeus.mapper.interfaces.FileDetailMapper;
import com.brihaspathee.zeus.producer.FileInfoProducer;
import com.brihaspathee.zeus.service.interfaces.FileService;
import com.brihaspathee.zeus.service.interfaces.FileStorageService;
import com.brihaspathee.zeus.service.interfaces.TradingPartnerService;
import com.brihaspathee.zeus.service.interfaces.TransactionOrigService;
import com.brihaspathee.zeus.test.ZeusTransactionControlNumber;
import com.brihaspathee.zeus.util.ZeusRandomStringGenerator;
import com.brihaspathee.zeus.web.model.TradingPartnerDto;
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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

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

    private final FileDetailMapper detailMapper;

    private final FileDetailRepository detailRepository;

    private final FileInfoProducer fileInfoProducer;

    private final TradingPartnerService tradingPartnerService;

    private final TransactionOrigService transactionOrigService;

    private final FileStorageService fileStorageService;

    @Override
    public void processFile(Resource resource, List<ZeusTransactionControlNumber> testTransactionControlNumbers) throws IOException {
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

        TradingPartnerDto tradingPartnerDto =
                tradingPartnerService.getTradingPartner(senderId, receiverId);
        log.info("Trading Partner Info:{}", tradingPartnerDto);

        FileDetail fileDetail = FileDetail.builder()
                .zeusFileControlNumber(ZeusRandomStringGenerator.randomString(15))
                .fileName(fileName)
                .fileReceivedDate(fileCreationTime)
                .tradingPartnerId(tradingPartnerDto.getTradingPartnerId())
                .lineOfBusinessTypeCode(tradingPartnerDto.getLineOfBusinessTypeCode())
                .businessUnitTypeCode(tradingPartnerDto.getBusinessUnitTypeCode())
                .senderId(tradingPartnerDto.getSenderId())
                .receiverId(tradingPartnerDto.getReceiverId())
                .marketplaceTypeCode(tradingPartnerDto.getMarketplaceTypeCode())
                .stateTypeCode(tradingPartnerDto.getStateTypeCode())
                //.fileData(fileData)
                .build();
        fileDetail = detailRepository.save(fileDetail);
        FileDetailDto fileDetailDto = detailMapper.fileDetailToFileDetailDto(fileDetail);
        fileDetailDto.setFileData(fileData);
        fileDetailDto.setTransactionControlNumbers(testTransactionControlNumbers);
        //fileInfoProducer.sendFileInfo(fileDetailDto);
        transactionOrigService.postFileDetails(fileDetailDto);
        fileStorageService.postFileDetails(fileDetailDto);
    }

    @Override
    public void testProcessFile(Resource resource) throws IOException {
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
    }


}
