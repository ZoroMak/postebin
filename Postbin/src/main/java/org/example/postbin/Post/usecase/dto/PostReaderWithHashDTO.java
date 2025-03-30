package org.example.postbin.Post.usecase.dto;

import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

@Value
public class PostReaderWithHashDTO implements Serializable {
    String title;
    String hashUrl;
    String url;
    String authorNickname;
    LocalDateTime createdAt;
}
