package org.example.postbin.Post.persistence.s3.usecase.download;

public interface DownloadFromCloudStorage {
    String execute(String hashUrl);
}
