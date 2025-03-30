package org.example.postbin.Post.usecase;

import org.example.postbin.Post.domain.Post;
import org.example.postbin.Post.usecase.dto.PostReaderWithHashDTO;

import java.util.Optional;

public interface SavePost {
    Optional<PostReaderWithHashDTO> execute(Post post);
}
