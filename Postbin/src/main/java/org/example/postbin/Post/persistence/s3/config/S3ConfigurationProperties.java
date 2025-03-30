package org.example.postbin.Post.persistence.s3.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties("s3-config")
public class S3ConfigurationProperties {
    private final String publicAccessKey;
    private final String publicSecretKey;
    private final String publicBucketName;
    private final String region;
    private final String endpointUrl;
}
