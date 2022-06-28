package com.brihaspathee.zeus.service.impl;

import com.brihaspathee.zeus.service.interfaces.TradingPartnerService;
import com.brihaspathee.zeus.web.model.TradingPartnerDto;
import com.brihaspathee.zeus.web.response.ZeusApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 30, March 2022
 * Time: 11:18 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.service.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TradingPartnerServiceImpl implements TradingPartnerService {

    @Value("${url.host.trading-partner}")
    private String tradingPartnerHost;

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    @Override
    public TradingPartnerDto getTradingPartner(String senderId, String receiverId) {
        ResponseEntity<ZeusApiResponse> response =
                restTemplate.getForEntity(tradingPartnerHost+"/api/v1/tp/" + senderId + "/" + receiverId, ZeusApiResponse.class);
        ZeusApiResponse apiResponse = response.getBody();
        TradingPartnerDto tradingPartnerDto =
                objectMapper.convertValue(apiResponse.getResponse(), TradingPartnerDto.class);
        return tradingPartnerDto;
    }
}
