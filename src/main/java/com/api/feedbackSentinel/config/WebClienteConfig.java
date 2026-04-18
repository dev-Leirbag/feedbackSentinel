package com.api.feedbackSentinel.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClienteConfig {

    @Value("${chat_gpt_url}")
    private String urlGpt;

    @Bean
    public WebClient webClient(WebClient.Builder Builder) {
        return Builder.baseUrl(urlGpt).build();
    }

}
