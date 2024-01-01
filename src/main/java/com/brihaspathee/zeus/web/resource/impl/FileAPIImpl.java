package com.brihaspathee.zeus.web.resource.impl;

import com.brihaspathee.zeus.dto.transaction.FileDetailDto;
import com.brihaspathee.zeus.web.resource.interfaces.FileAPI;
import com.brihaspathee.zeus.web.response.ZeusApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 05, March 2022
 * Time: 9:41 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.web.resource.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class FileAPIImpl implements FileAPI {
    @Override
    public ResponseEntity<ZeusApiResponse<FileDetailDto>> getFileDetail(String fileId) {
        FileDetailDto fileDetailDto = FileDetailDto.builder().fileName("Test").build();
        ZeusApiResponse<FileDetailDto> apiResponse = ZeusApiResponse.<FileDetailDto>builder()
                .response(fileDetailDto)
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}
