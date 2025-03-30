package org.example.postbin.Post.domain;

import java.io.Serializable;

public record Author(Long id, String name, String surname, String nickname) implements Serializable {
    private static final String REGEX = "^[A-Za-zА-Яа-яЁё]+$";

    public Author {
        checkName(name);
        checkSurname(surname);
    }

    private void checkName(String name) {
        if (name == null)
            return;

        if (!name.matches(REGEX))
            throw new IllegalArgumentException("This string can not be name author");
    }

    private void checkSurname(String surname) {
        if (surname == null)
            return;

        if (!surname.matches(REGEX))
            throw new IllegalArgumentException("This string can not be name author");
    }
}
