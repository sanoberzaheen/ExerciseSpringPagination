package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebConfiguration {
	
    @Value("${eucountries.base.url}")
    private String countriesBaseUrl;

	   @Bean
	    public WebClient.Builder webClientBuilder() {
	        return WebClient.builder().baseUrl(countriesBaseUrl);
	    }
}
