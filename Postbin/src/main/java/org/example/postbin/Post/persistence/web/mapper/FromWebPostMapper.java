package org.example.postbin.Post.persistence.web.mapper;

import org.example.postbin.Post.domain.Author;
import org.example.postbin.Post.domain.Post;
import org.example.postbin.Post.domain.Title;
import org.example.postbin.Post.persistence.web.WebPost;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class FromWebPostMapper implements Mapper<Post, WebPost> {
    @Override
    public Post map(WebPost from) {

        return Post.builder()
                .title(new Title(from.getTitle()))
                .description(from.getDescription())
                .author(new Author(null, from.getName(), from.getSurname(), from.getNickname()))
                .deletedAfterSomeDays(LocalDateTime.now().plusDays(from.getDeletedAfterSomeDays()))
                .build();
    }
}
