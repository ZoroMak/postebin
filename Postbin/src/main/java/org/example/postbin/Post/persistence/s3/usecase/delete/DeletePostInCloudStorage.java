package org.example.postbin.Post.persistence.s3.usecase.delete;

public interface DeletePostInCloudStorage {
    void execute(String hash);
}
