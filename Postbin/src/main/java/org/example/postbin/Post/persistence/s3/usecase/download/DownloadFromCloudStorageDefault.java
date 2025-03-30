package org.example.postbin.Post.persistence.s3.usecase.download;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Repository
@RequiredArgsConstructor
public class DownloadFromCloudStorageDefault implements DownloadFromCloudStorage {
    private final S3Client client;
    private final String bucketName;
    public String execute(String hashUrl) {
        return downloadFile(hashUrl);
    }
    private String downloadFile(String key) {
        if (key == null)
            return null;

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        try (InputStream inputStream = client.getObject(getObjectRequest);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }

            return outputStream.toString(StandardCharsets.UTF_8).trim();
        } catch (IOException e) {
            throw new RuntimeException("Error downloading file from cloud storage", e);
        }
    }
}
