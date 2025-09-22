package com.example.config;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {

    @Bean
    public MinioClient minioClient() {
        return  MinioClient.builder()
                .endpoint("http://localhost:9090")
                .credentials("minioadmin","minioadmin")
                .build();
    }
}
