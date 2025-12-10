package com.smarttravel.paymentservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${service.booking.url}")
    private String bookingServiceUrl;

    @Bean
    public WebClient bookingWebClient() {
        return WebClient.builder()
                .baseUrl(bookingServiceUrl)
                .build();
    }
}
