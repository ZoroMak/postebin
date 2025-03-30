package org.example.postbin.Post.persistence.s3.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

@Configuration
@EnableConfigurationProperties(S3ConfigurationProperties.class)
@RequiredArgsConstructor
public class S3Configuration {
    private final S3ConfigurationProperties properties;

    @Bean
    public String bucketName() {
        return properties.getPublicBucketName();
    }

    @Bean
    public Region region() {
        return Region.of(properties.getRegion());
    }

    @Bean
    public URI endpointUrl() {
        return URI.create(properties.getEndpointUrl());
    }

    @Bean
    public AwsCredentials awsCredentials() {
        return AwsBasicCredentials.create(properties.getPublicAccessKey(), properties.getPublicSecretKey());
    }

    @Bean
    public S3Client s3Client() {
        return S3Client
                .builder()
                .region(region())
                .credentialsProvider(this::awsCredentials)
                .endpointOverride(endpointUrl())
                .build();
    }
}
