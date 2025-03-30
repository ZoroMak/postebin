package org.example.postbin.Post.persistence.db.mapper;

import org.example.postbin.Post.domain.Author;
import org.example.postbin.Post.persistence.db.model.AuthorData;
import org.springframework.stereotype.Component;

@Component
public class FromDBAuthorMapper implements Mapper<AuthorData, Author> {
    @Override
    public Author map(AuthorData from) {
        return new Author(
                from.getId(),
                from.getName(),
                from.getSurname(),
                from.getNickname());
    }
}
