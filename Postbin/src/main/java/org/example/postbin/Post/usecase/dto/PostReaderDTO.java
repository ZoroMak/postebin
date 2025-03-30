package org.example.postbin.Post.usecase.dto;

import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

@Value
public class PostReaderDTO implements Serializable {
    Long id;
    String title;
    String description;
    String authorNickname;
    LocalDateTime createdAt;
}
