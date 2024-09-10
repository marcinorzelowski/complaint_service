package com.orzelowski.complaintservice.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfiguration {
    private static final String IP_API_URL = "http://ip-api.com/json/";

    @Bean(name = "IPLocationClient")
    public WebClient ipLocationClient() {
        return WebClient.builder()
                .baseUrl(IP_API_URL)
                .build();
    }
}
