package org.example.postbin.integration.repository;

import jakarta.transaction.Transactional;
import org.example.postbin.Post.domain.Author;
import org.example.postbin.Post.domain.Page;
import org.example.postbin.Post.domain.Post;
import org.example.postbin.Post.domain.Title;
import org.example.postbin.Post.domain.filter.PostFilter;
import org.example.postbin.Post.persistence.db.MainFunctionalUseCaseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class MainFunctionalUseCaseRepositoryTest {
    @Autowired
    private MainFunctionalUseCaseRepository repository;
    private final Page page = new Page(0, 2);
    private final LocalDateTime now = LocalDateTime.now();

    @Test
    public void test_find_by_hash_url() {
        Optional<Post> post = repository.getPostByHashUrl("13579246");

        assertTrue(post.isPresent());
        Post findpost = post.get();

        assertEquals("Post 3", findpost.getTitle().title());
        assertEquals("peterjones", findpost.getAuthor().nickname());
        assertEquals(2024, findpost.getCreatedAt().getYear());
        assertEquals(12, findpost.getCreatedAt().getMonth().getValue());
        assertEquals(10, findpost.getCreatedAt().getDayOfMonth());
    }

    @Test
    public void test_save_post_in_db() {
        Post post = Post.builder()
                .title(new Title("Test Title"))
                .hashUrl("12345678")
                .author(new Author(null, null, null, "johndoe"))
                .deletedAfterSomeDays(now.plusDays(1))
                .createdAt(now)
                .build();

        Optional<Post> savedPost = repository.save(post);

        assertTrue(savedPost.isPresent());

        Post savedPost1 = savedPost.get();

        assertTrue(savedPost1.getId() > 0);
        assertEquals(post.getTitle(), savedPost1.getTitle());
        assertEquals(post.getAuthor().nickname(), savedPost1.getAuthor().nickname());
        assertEquals(post.getCreatedAt(), savedPost1.getCreatedAt());
    }

    @Test
    public void test_find_by_filter_without_title() {
        PostFilter filter = new PostFilter(null, "johndoe");

        List<Post> actual = repository.findPostByFilter(filter, page);

        assertEquals(1, actual.size());
        Post savedPost = actual.get(0);
        assertEquals("Post 1", savedPost.getTitle().title());
        assertEquals("12345678", savedPost.getHashUrl());
        assertEquals("johndoe", savedPost.getAuthor().nickname());
    }

    @Test
    public void test_find_by_filter_without_nickname() {
        PostFilter filter = new PostFilter("Post 1", null);

        List<Post> actual = repository.findPostByFilter(filter, page);

        assertEquals(1, actual.size());
        Post savedPost = actual.get(0);
        assertEquals("Post 1", savedPost.getTitle().title());
        assertEquals("12345678", savedPost.getHashUrl());
        assertEquals("johndoe", savedPost.getAuthor().nickname());
    }

    @Test
    public void test_find_by_filter_with_title_and_nickname() {
        PostFilter filter = new PostFilter("Post", "jo");

        List<Post> actual = repository.findPostByFilter(filter, page);

        assertEquals(2, actual.size());
        Post savedPost = actual.get(0);
        assertEquals("Post 1", savedPost.getTitle().title());
        assertEquals("12345678", savedPost.getHashUrl());
        assertEquals("johndoe", savedPost.getAuthor().nickname());

        savedPost = actual.get(1);
        assertEquals("Post 3", savedPost.getTitle().title());
        assertEquals("13579246", savedPost.getHashUrl());
        assertEquals("peterjones", savedPost.getAuthor().nickname());
    }

}
