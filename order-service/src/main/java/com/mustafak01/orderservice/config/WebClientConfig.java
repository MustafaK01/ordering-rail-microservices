package com.mustafak01.orderservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    //I used WebClient because it is alternative and more modern of RestTemplate according to spring boot docs.
    //And it supports async, sync and streaming scenarios.

    @Bean
    public WebClient webClient(){
        return WebClient.builder().build();
    }

}
