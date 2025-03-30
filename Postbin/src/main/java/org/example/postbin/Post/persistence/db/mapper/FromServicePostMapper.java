package org.example.postbin.Post.persistence.db.mapper;

import lombok.RequiredArgsConstructor;
import org.example.postbin.Post.domain.Post;
import org.example.postbin.Post.persistence.db.model.PostData;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FromServicePostMapper implements Mapper<Post, PostData> {
    private final FromServiceAuthorMapper fromServiceAuthorMapper;
    @Override
    public PostData map(Post from) {
        return PostData.builder()
                .id(from.getId())
                .title(from.getTitle().title())
                .hashUrl(from.getHashUrl())
                .author(fromServiceAuthorMapper.map(from.getAuthor()))
                .version(from.getVersion())
                .deletedAfterSomeDays(from.getDeletedAfterSomeDays())
                .createdAt(from.getCreatedAt())
                .build();
    }
}

