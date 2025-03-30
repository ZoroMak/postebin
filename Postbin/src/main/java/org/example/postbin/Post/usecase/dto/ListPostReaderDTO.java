package org.example.postbin.Post.usecase.dto;

import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

@Value
public class ListPostReaderDTO implements Serializable {
    Long id;
    String title;
    String hash;
    String authorNickname;
    LocalDateTime createdAt;
}
