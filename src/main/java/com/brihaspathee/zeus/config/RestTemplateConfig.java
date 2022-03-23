package com.brihaspathee.zeus.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 22, March 2022
 * Time: 12:44 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.config
 * To change this template use File | Settings | File and Code Template
 */
@Component
public class RestTemplateConfig {

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplateBuilder().basicAuthentication("ref-data", "password").build();
    }
}
