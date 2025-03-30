package org.example.postbin.Post.persistence.db.mapper;

import lombok.RequiredArgsConstructor;
import org.example.postbin.Post.domain.Author;
import org.example.postbin.Post.domain.Post;
import org.example.postbin.Post.domain.Title;
import org.example.postbin.Post.persistence.db.model.PostData;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FromDBPostMapper implements Mapper<PostData, Post> {
    private final FromDBAuthorMapper fromDBAuthorMapper;

    @Override
    public Post map(PostData from) {
        Author author = Optional.ofNullable(from.getAuthor())
                .map(fromDBAuthorMapper::map)
                .orElse(null);

        return Post.builder()
                .id(from.getId())
                .hashUrl(from.getHashUrl())
                .title(new Title(from.getTitle()))
                .author(author)
                .version(from.getVersion())
                .createdAt(from.getCreatedAt())
                .build();
    }
}
