package org.example.postbin.Post.usecase.mapper;

import org.example.postbin.Post.domain.Post;
import org.example.postbin.Post.usecase.dto.PostReaderDTO;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class PostReaderMapper implements Mapper<Post, PostReaderDTO> {
    @Override
    public PostReaderDTO map(Post from) {
        return new PostReaderDTO(
                from.getId(),
                from.getTitle().title(),
                from.getDescription(),
                from.getAuthor().nickname(),
                from.getCreatedAt()
        );
    }
}
