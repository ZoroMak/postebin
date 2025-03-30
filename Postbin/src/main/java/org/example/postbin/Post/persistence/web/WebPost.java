package org.example.postbin.Post.persistence.web;

import lombok.Value;

@Value
public class WebPost {
    String title;
    String description;
    String name;
    String surname;
    String nickname;
    Integer deletedAfterSomeDays;
}
