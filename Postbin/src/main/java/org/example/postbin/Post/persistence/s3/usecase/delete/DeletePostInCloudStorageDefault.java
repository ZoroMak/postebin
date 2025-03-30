package org.example.postbin.Post.persistence.s3.usecase.delete;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;

@Repository
@RequiredArgsConstructor
public class DeletePostInCloudStorageDefault implements DeletePostInCloudStorage{
    private final S3Client client;
    private final String bucketName;
    @Override
    public void execute(String hash) {
        if (hash == null)
            return;

        DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(hash)
                .build();
        client.deleteObject(deleteRequest);
    }
}
