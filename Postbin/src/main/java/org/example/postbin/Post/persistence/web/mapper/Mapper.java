package org.example.postbin.Post.persistence.web.mapper;

public interface Mapper <T, F> {
    T map(F from);
}
