package org.example.postbin.Post.persistence.s3.usecase.upload;

import org.example.postbin.Post.domain.Post;

public interface UploadToObjectStorage {

    void execute(Post post);
}
