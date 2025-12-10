package com.smarttravel.bookingservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${service.user.url}")
    private String userServiceUrl;

    @Value("${service.payment.url}")
    private String paymentServiceUrl;

    @Value("${service.notification.url}")
    private String notificationServiceUrl;

    @Bean
    public WebClient userWebClient() {
        return WebClient.builder()
                .baseUrl(userServiceUrl)
                .build();
    }

    @Bean
    public WebClient paymentWebClient() {
        return WebClient.builder()
                .baseUrl(paymentServiceUrl)
                .build();
    }

    @Bean
    public WebClient notificationWebClient() {
        return WebClient.builder()
                .baseUrl(notificationServiceUrl)
                .build();
    }
}
