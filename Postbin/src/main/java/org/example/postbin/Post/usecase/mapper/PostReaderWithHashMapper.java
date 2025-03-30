package org.example.postbin.Post.usecase.mapper;

import org.example.postbin.Post.domain.Post;
import org.example.postbin.Post.usecase.dto.PostReaderWithHashDTO;
import org.springframework.stereotype.Component;

@Component
public class PostReaderWithHashMapper implements Mapper<Post, PostReaderWithHashDTO> {
    @Override
    public PostReaderWithHashDTO map(Post from) {
        return new PostReaderWithHashDTO(
                from.getTitle().title(),
                from.getHashUrl(),
                from.getUrl(),
                from.getAuthor().nickname(),
                from.getCreatedAt()
        );
    }
}
