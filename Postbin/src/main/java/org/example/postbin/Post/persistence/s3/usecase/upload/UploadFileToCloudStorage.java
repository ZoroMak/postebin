package org.example.postbin.Post.persistence.s3.usecase.upload;

import lombok.RequiredArgsConstructor;
import org.example.postbin.Post.domain.Post;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

@Repository
@RequiredArgsConstructor
public class UploadFileToCloudStorage implements UploadToObjectStorage {
    private final S3Client client;
    private final String bucketName;

    public void execute(Post post) {
        if (post == null)
            return;

        String key = post.getHashUrl();
        File file = null;

        try {
            file = convertToFile(post.getDescription(), key);
            PutObjectRequest putRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();
            client.putObject(putRequest, RequestBody.fromFile(file));
        } finally {
            if (file != null && file.exists()) {
                if (!file.delete()) {
                    System.err.println("Failed to delete temporary file: " + file.getAbsolutePath());
                }
            }
        }
    }
    private File convertToFile(String postDescription, String hashName) {
        File file = new File(hashName + ".txt");

        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
            writer.write(postDescription);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return file;
    }
}
