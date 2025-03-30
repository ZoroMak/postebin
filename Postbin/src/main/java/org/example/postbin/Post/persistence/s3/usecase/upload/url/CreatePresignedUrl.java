package org.example.postbin.Post.persistence.s3.usecase.upload.url;

public interface CreatePresignedUrl {
    String execute(String hashUrl, long activeDays);
}
