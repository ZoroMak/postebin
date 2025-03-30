package org.example.postbin.Post.usecase.access;

import org.example.postbin.Post.domain.Page;
import org.example.postbin.Post.domain.Post;
import org.example.postbin.Post.domain.filter.PostFilter;

import java.util.List;
import java.util.Optional;

public interface PostExtractor {
    Optional<Post> getPostByHashUrl(String hashUrl);
    List<Post> findPostByFilter(PostFilter filter, Page page);
    Long count(PostFilter filter);
}
