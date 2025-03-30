package org.example.postbin.integration;

import jakarta.transaction.Transactional;
import org.example.postbin.Post.domain.Author;
import org.example.postbin.Post.domain.Post;
import org.example.postbin.Post.domain.Title;
import org.example.postbin.Post.persistence.s3.usecase.upload.url.CreatePresignedUrl;
import org.example.postbin.Post.persistence.s3.usecase.upload.UploadToObjectStorage;
import org.example.postbin.Post.usecase.GetPostByHashUrl;
import org.example.postbin.Post.usecase.SavePost;
import org.example.postbin.Post.usecase.dto.PostReaderDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@Transactional
@EnableCaching
public class RedisTest {
    @Autowired
    private GetPostByHashUrl service;
    @Autowired
    private SavePost saveService;
    @Mock
    private UploadToObjectStorage uploadToCloudStorage;
    @Mock
    private CreatePresignedUrl createPresignedGetUrl;
    private final static LocalDateTime now = LocalDateTime.now();
    @Test
    void givenRedisCaching_whenFindItemById_thenItemReturnedFromCache() {
        Post anItem = Post.builder()
                        .hashUrl("12345678")
                        .build();

        Optional<PostReaderDTO> itemCacheMiss = service.execute(anItem.getHashUrl());
        Optional<PostReaderDTO>  itemCacheHit = service.execute(anItem.getHashUrl());

        assertTrue(itemCacheMiss.isPresent());
        assertTrue(itemCacheHit.isPresent());

        PostReaderDTO postCacheHit = itemCacheHit.get();
        PostReaderDTO postCacheMiss = itemCacheMiss.get();

        assertEquals(postCacheHit.getTitle(), postCacheMiss.getTitle());
        assertEquals(postCacheHit.getAuthorNickname(), postCacheMiss.getAuthorNickname());
        assertEquals(postCacheHit.getDescription(), postCacheMiss.getDescription());
    }

    @Test
    void test_give_from_redis_after_save_post_in_db() {
        Post post = Post.builder()
                .title(new Title("Test Title"))
                .hashUrl("12345679")
                .author(new Author(null, "Test", "Test", "test"))
                .description("Test Description")
                .deletedAfterSomeDays(now.plusDays(1))
                .createdAt(now)
                .build();

        Mockito.doNothing().when(uploadToCloudStorage).execute(any(Post.class));
        Mockito.when(createPresignedGetUrl.execute(any(String.class), any(Long.class))).thenReturn("url");

        saveService.execute(post);

        Optional<PostReaderDTO> postInCash = service.execute(post.getHashUrl());

        assertTrue(postInCash.isPresent());

        PostReaderDTO postObject = postInCash.get();

        assertEquals(postObject.getTitle(), post.getTitle().title());
        assertEquals(postObject.getAuthorNickname(), post.getAuthor().nickname());
        assertEquals(postObject.getDescription(), post.getDescription());
        assertEquals(postObject.getCreatedAt(), post.getCreatedAt());
    }
}
