package org.example.postbin.Post.persistence.db.mapper;

import org.example.postbin.Post.domain.Author;
import org.example.postbin.Post.persistence.db.model.AuthorData;
import org.springframework.stereotype.Component;

@Component
public class FromServiceAuthorMapper implements Mapper<Author, AuthorData> {
    @Override
    public AuthorData map(Author from) {
        return AuthorData.builder()
                .name(from.name())
                .surname(from.surname())
                .nickname(from.nickname())
                .build();
    }
}
