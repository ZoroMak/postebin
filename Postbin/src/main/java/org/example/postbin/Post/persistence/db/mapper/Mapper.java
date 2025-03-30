package org.example.postbin.Post.persistence.db.mapper;

public interface Mapper <F, T>{
    T map(F from);
}

