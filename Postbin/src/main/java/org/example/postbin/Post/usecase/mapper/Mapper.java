package org.example.postbin.Post.usecase.mapper;

public interface Mapper <F, T>{
    T map(F from);
}
