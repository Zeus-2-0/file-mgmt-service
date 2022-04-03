package com.brihaspathee.zeus.mapper.interfaces;

import com.brihaspathee.zeus.domain.entity.FileDetail;
import com.brihaspathee.zeus.web.model.FileDetailDto;

import java.util.Set;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 30, March 2022
 * Time: 3:47 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.mapper.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface FileDetailMapper {

    FileDetail fileDetailDtoToFileDetail(FileDetailDto detailDto);
    FileDetailDto fileDetailToFileDetailDto(FileDetail detail);
    Set<FileDetail> fileDetailDtosToFileDetails(Set<FileDetailDto> detailDtos);
    Set<FileDetailDto> fileDetailsToFileDetailDtos(Set<FileDetail> details);
}
