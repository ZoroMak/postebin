package org.example.postbin.Post.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Post implements Serializable {
    private Long id;
    private Title title;
    private String description;
    private String hashUrl;
    private String url;
    private Author author;
    private Long version;
    private LocalDateTime deletedAfterSomeDays;
    private LocalDateTime createdAt;
}
