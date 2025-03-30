package org.example.postbin.Post.domain;

import java.io.Serializable;

public record Title(String title) implements Serializable {
    public Title {
        checkTitle(title);
    }

    private void checkTitle(String title) {
        if (title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null");
        }

        if (title.length() > 100) {
            throw new IllegalArgumentException("Title must be less than 100 characters");
        }
    }
}
