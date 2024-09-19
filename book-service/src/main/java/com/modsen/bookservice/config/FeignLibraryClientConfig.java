package com.modsen.bookservice.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignLibraryClientConfig {

    private final String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJsaWJyYXJ5LXNlcnZpY2UiLCJuYW1lIjoiYWRtaW4iLCJyb2xlIjoiYWRtaW4iLCJpYXQiOjE2OTUxMzkyMDAsImV4cCI6MTY5NTE0MjgwMCwiYXVkIjoibGlicmFyeS1zZXJ2aWNlIn0.XcMx0SXBv6Kw8r1wqeJZpPVHgITovPv7mlymg6F-1mc";

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                requestTemplate.header("Authorization", "Bearer " + token);
            }
        };
    }
}
