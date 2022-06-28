package com.brihaspathee.zeus.mapper.impl;

import com.brihaspathee.zeus.domain.entity.FileDetail;
import com.brihaspathee.zeus.mapper.interfaces.FileDetailMapper;
import com.brihaspathee.zeus.web.model.FileDetailDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 30, March 2022
 * Time: 3:52 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.mapper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
public class FileDetailMapperImpl implements FileDetailMapper {

    @Override
    public FileDetail fileDetailDtoToFileDetail(FileDetailDto detailDto) {
        if(detailDto == null){
            return null;
        }
        FileDetail fileDetail = FileDetail.builder()
                .fileDetailSK(detailDto.getFileDetailSK())
                .zeusFileControlNumber(detailDto.getZeusFileControlNumber())
                .fileName(detailDto.getFileName())
                .fileReceivedDate(detailDto.getFileReceivedDate())
                .senderId(detailDto.getSenderId())
                .receiverId(detailDto.getReceiverId())
                .tradingPartnerId(detailDto.getTradingPartnerId())
                .lineOfBusinessTypeCode(detailDto.getLineOfBusinessTypeCode())
                .marketplaceTypeCode(detailDto.getMarketplaceTypeCode())
                .stateTypeCode(detailDto.getStateTypeCode())
                .build();
        return fileDetail;
    }

    @Override
    public FileDetailDto fileDetailToFileDetailDto(FileDetail detail) {
        if(detail == null){
            return null;
        }
        FileDetailDto fileDetailDto = FileDetailDto.builder()
                .fileDetailSK(detail.getFileDetailSK())
                .zeusFileControlNumber(detail.getZeusFileControlNumber())
                .fileName(detail.getFileName())
                .fileReceivedDate(detail.getFileReceivedDate())
                .senderId(detail.getSenderId())
                .receiverId(detail.getReceiverId())
                .tradingPartnerId(detail.getTradingPartnerId())
                .lineOfBusinessTypeCode(detail.getLineOfBusinessTypeCode())
                .marketplaceTypeCode(detail.getMarketplaceTypeCode())
                .stateTypeCode(detail.getStateTypeCode())
                .build();
        return fileDetailDto;
    }

    @Override
    public Set<FileDetail> fileDetailDtosToFileDetails(Set<FileDetailDto> detailDtos) {
        return detailDtos.stream().map(this::fileDetailDtoToFileDetail).collect(Collectors.toSet());
    }

    @Override
    public Set<FileDetailDto> fileDetailsToFileDetailDtos(Set<FileDetail> details) {
        return details.stream().map(this::fileDetailToFileDetailDto).collect(Collectors.toSet());
    }
}
