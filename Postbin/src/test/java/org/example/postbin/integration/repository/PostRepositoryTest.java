package org.example.postbin.integration.repository;

import jakarta.transaction.Transactional;
import org.example.postbin.Post.persistence.db.model.AuthorData;
import org.example.postbin.Post.persistence.db.model.PostData;
import org.example.postbin.Post.persistence.db.repository.AuthorRepository;
import org.example.postbin.Post.persistence.db.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class PostRepositoryTest {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private AuthorRepository authorRepository;
    private final static LocalDateTime now = LocalDateTime.now();


    @Test
    public void test_save_post_in_db() {
        AuthorData authorData = AuthorData.builder()
                .name("John")
                .surname("Doe")
                .nickname("johne")
                .build();

        authorRepository.save(authorData);

        PostData postData = PostData.builder()
                .title("Test Title")
                .hashUrl("12345678")
                .author(authorData)
                .deletedAfterSomeDays(now.plusDays(1))
                .createdAt(now)
                .build();

        PostData savedPost = postRepository.save(postData);

        assertEquals(postData, savedPost);
        assertTrue(savedPost.getId() > 0);
        assertEquals(postData.getTitle(), savedPost.getTitle());
        assertEquals(postData.getAuthor(), savedPost.getAuthor());
        assertEquals(postData.getHashUrl(), savedPost.getHashUrl());
        assertEquals(postData.getDeletedAfterSomeDays(), savedPost.getDeletedAfterSomeDays());
        assertEquals(postData.getCreatedAt(), savedPost.getCreatedAt());
    }


    @Test
    public void test_find_by_hash_url() {
        Optional<PostData> postData = postRepository.findByHashUrl("13579246");

        assertTrue(postData.isPresent());
        PostData post = postData.get();

        assertEquals("Post 3", post.getTitle());
        assertEquals("13579246", post.getHashUrl());
        assertEquals("peterjones", post.getAuthor().getNickname());
        assertEquals(2024, post.getCreatedAt().getYear());
        assertEquals(12, post.getCreatedAt().getMonth().getValue());
        assertEquals(10, post.getCreatedAt().getDayOfMonth());
    }
}