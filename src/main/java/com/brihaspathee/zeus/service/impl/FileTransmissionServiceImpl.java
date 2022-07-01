package com.brihaspathee.zeus.service.impl;

import com.brihaspathee.zeus.domain.entity.FileAcknowledgement;
import com.brihaspathee.zeus.domain.entity.FileDetail;
import com.brihaspathee.zeus.domain.repository.FileAcknowledgementRepository;
import com.brihaspathee.zeus.service.interfaces.FileTransmissionService;
import com.brihaspathee.zeus.web.model.FileDetailDto;
import com.brihaspathee.zeus.web.model.FileResponseDto;
import com.brihaspathee.zeus.web.response.ZeusApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 22, June 2022
 * Time: 11:52 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.service.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FileTransmissionServiceImpl implements FileTransmissionService {

    private final RestTemplate restTemplate;

    private final WebClient webClient;

    private final FileAcknowledgementRepository acknowledgementRepository;

    @Override
    public void postFileDetails(String url, FileDetailDto fileDetailDto) {
            useWebClient(url,fileDetailDto);
    }

    /**
     * Uses web client to make the REST API Call
     * @param fileDetailDto
     */
    private void useWebClient(String host, FileDetailDto fileDetailDto){
        Mono<ZeusApiResponse<FileResponseDto>> apiResponseMono = webClient.post()
                .uri(host)
                .body(Mono.just(fileDetailDto), FileDetailDto.class)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ZeusApiResponse<FileResponseDto>>() {});
        apiResponseMono.doOnError(exception -> {
            log.info("Some Exception occurred:{}", exception.getMessage());
        }).subscribe(response -> {
            log.info("Web Client API Response:{}", response.getResponse().getFileReceiptAck());
            FileAcknowledgement fileAcknowledgement = FileAcknowledgement.builder()
                    .fileDetail(FileDetail.builder()
                            .fileDetailSK(fileDetailDto.getFileDetailSK())
                            .build())
                    .acknowledgement(response.getResponse().getFileReceiptAck())
                    .ackSource(response.getResponse().getServiceName())
                    .build();
            acknowledgementRepository.save(fileAcknowledgement);
        });

    }

    /**
     * This method uses rest template to make the call to service
     * @param fileDetailDto
     */
    private void useRestTemplate(String host, FileDetailDto fileDetailDto){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<FileDetailDto> entity = new HttpEntity<>(fileDetailDto, headers);
        ResponseEntity<ZeusApiResponse<FileResponseDto>> apiResponse = restTemplate.exchange(
                host,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<ZeusApiResponse<FileResponseDto>>() {
                });
        log.info("Rest Template API Response:{}", apiResponse.getBody().getResponse().getFileReceiptAck());
    }
}
