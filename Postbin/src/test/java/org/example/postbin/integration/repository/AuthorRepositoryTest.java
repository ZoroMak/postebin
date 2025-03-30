package org.example.postbin.integration.repository;

import jakarta.transaction.Transactional;
import org.example.postbin.Post.persistence.db.model.AuthorData;
import org.example.postbin.Post.persistence.db.repository.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class AuthorRepositoryTest {
    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void test_find_by_nickname() {
        AuthorData authorData = AuthorData.builder()
                .name("John")
                .surname("Doe")
                .nickname("johne")
                .build();

        authorRepository.save(authorData);

        Optional<AuthorData> author = authorRepository.findByNickname("johne");

        assertTrue(author.isPresent());
        assertEquals(authorData, author.get());
        assertEquals(authorData.getName(), author.get().getName());
        assertEquals(authorData.getSurname(), author.get().getSurname());
        assertEquals(authorData.getNickname(), author.get().getNickname());
    }
}
