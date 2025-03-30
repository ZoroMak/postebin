package org.example.postbin.integration;

import jakarta.transaction.Transactional;
import org.example.postbin.Post.domain.Author;
import org.example.postbin.Post.domain.Post;
import org.example.postbin.Post.domain.Title;
import org.example.postbin.Post.persistence.db.MainFunctionalUseCaseRepository;
import org.example.postbin.Post.persistence.db.mapper.FromServicePostMapper;
import org.example.postbin.Post.persistence.db.model.PostData;
import org.example.postbin.Post.persistence.db.repository.PostRepository;
import org.example.postbin.Post.usecase.FindPostByFilter;
import org.example.postbin.Post.usecase.GetPostByHashUrl;
import org.example.postbin.Post.usecase.SavePost;
import org.example.postbin.Post.usecase.dto.PostReaderDTO;
import org.example.postbin.Post.usecase.dto.PostReaderWithHashDTO;
import org.example.postbin.Post.usecase.scenarios.FindPostByHashUrlUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ObjectCloudTest {
    @Autowired
    private SavePost repository;
    @Autowired
    private GetPostByHashUrl useCase;
    @Autowired
    private FromServicePostMapper fromServicePostMapper;
    @Mock
    private PostRepository postRepository;
    private final LocalDateTime now = LocalDateTime.now();
    private Post post;

    @BeforeEach
    public void setup() {
        post = Post.builder()
                .title(new Title("Test Title"))
                .hashUrl("12345678")
                .author(new Author(null, null, null, "johndoe"))
                .description("Test Description")
                .deletedAfterSomeDays(now.plusDays(1))
                .createdAt(now)
                .build();
    }

    @Test
    public void test_upload_to_cloud() {
        PostData postData = fromServicePostMapper.map(post);

        Mockito.when(postRepository.save(any(PostData.class))).thenReturn(postData);

        Optional<PostReaderWithHashDTO> savedPost = repository.execute(post);

        assertTrue(savedPost.isPresent());
        System.out.println(savedPost.get());
    }

    @Test
    public void test_download_from_cloud() {
        final String hashUrl = "EhENlYI2";

        Mockito.when(postRepository.findByHashUrl(any(String.class))).thenReturn(Optional.of(new PostData()));

        Optional<PostReaderDTO> post = useCase.execute(hashUrl);

        assertTrue(post.isPresent());
        assertEquals("Test Description", post.get().getDescription());
    }
}
