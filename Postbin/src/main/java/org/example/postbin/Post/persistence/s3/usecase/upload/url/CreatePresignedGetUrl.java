package org.example.postbin.Post.persistence.s3.usecase.upload.url;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.net.URI;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class CreatePresignedGetUrl implements CreatePresignedUrl {
    private final AwsCredentials credentials;
    private final S3Client client;
    private final String bucketName;
    private final Region region ;
    private final URI endpointUrl;
    @Override
    public String execute(String hashUrl, long activeDays) {
        try (S3Presigner presigner = S3Presigner.builder()
                .s3Client(client)
                .credentialsProvider(() -> credentials)
                .region(region)
                .endpointOverride(endpointUrl)
                .build()) {

            GetObjectRequest objectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(hashUrl)
                    .build();

            GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofDays(activeDays))
                    .getObjectRequest(objectRequest)
                    .build();

            PresignedGetObjectRequest presignedRequest = presigner.presignGetObject(presignRequest);

            return presignedRequest.url().toExternalForm();
        }
    }
}