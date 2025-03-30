package org.example.postbin.Post.usecase.mapper;

import org.example.postbin.Post.domain.Post;
import org.example.postbin.Post.usecase.dto.ListPostReaderDTO;
import org.springframework.stereotype.Component;

@Component
public class ListPostReaderMapper implements Mapper<Post, ListPostReaderDTO> {
    @Override
    public ListPostReaderDTO map(Post from) {
        return new ListPostReaderDTO(
                from.getId(),
                from.getTitle().title(),
                from.getHashUrl(),
                from.getAuthor().nickname(),
                from.getCreatedAt());
    }
}
