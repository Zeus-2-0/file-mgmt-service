package com.brihaspathee.zeus.service.impl;

import com.brihaspathee.zeus.service.interfaces.FileTransmissionService;
import com.brihaspathee.zeus.service.interfaces.TransactionOrigService;
import com.brihaspathee.zeus.web.model.FileDetailDto;
import com.brihaspathee.zeus.web.model.FileResponseDto;
import com.brihaspathee.zeus.web.response.ZeusApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 21, June 2022
 * Time: 3:52 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.service.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionOrigServiceImpl implements TransactionOrigService {

    @Value("${url.host.transaction-orig-service}")
    private String transOrigHost;

    private final FileTransmissionService fileTransmissionService;

    @Override
    public void postFileDetails(FileDetailDto fileDetailDto) {
        fileTransmissionService.postFileDetails(transOrigHost + "/api/v1/trans-orig",fileDetailDto);
    }

}
