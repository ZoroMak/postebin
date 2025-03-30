package org.example.postbin.Post.usecase;

import org.example.postbin.Post.domain.Post;
import org.example.postbin.Post.usecase.dto.PostReaderDTO;

import java.util.Optional;

public interface GetPostByHashUrl {
    Optional<PostReaderDTO> execute(String hashUrl);
}
