package com.brihaspathee.zeus.service.impl;

import com.brihaspathee.zeus.service.interfaces.FileStorageService;
import com.brihaspathee.zeus.service.interfaces.FileTransmissionService;
import com.brihaspathee.zeus.web.model.FileDetailDto;
import com.brihaspathee.zeus.web.model.FileResponseDto;
import com.brihaspathee.zeus.web.response.ZeusApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 21, June 2022
 * Time: 4:47 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.service.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {

    @Value("${url.host.file-storage-service}")
    private String fileStorageHost;

    private final FileTransmissionService fileTransmissionService;

    @Override
    public void postFileDetails(FileDetailDto fileDetailDto) {
        fileTransmissionService.postFileDetails(fileStorageHost + "/api/v1/file-storage",fileDetailDto);
    }
}
