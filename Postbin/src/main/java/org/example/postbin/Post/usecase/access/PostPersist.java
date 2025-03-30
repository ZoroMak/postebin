package org.example.postbin.Post.usecase.access;

import org.example.postbin.Post.domain.Post;

import java.util.Optional;

public interface PostPersist {
    Optional<Post> save(Post post);
}
