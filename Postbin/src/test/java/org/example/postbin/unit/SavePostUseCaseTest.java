package org.example.postbin.unit;

import org.example.postbin.Post.domain.Author;
import org.example.postbin.Post.domain.Post;
import org.example.postbin.Post.domain.Title;
import org.example.postbin.Post.persistence.s3.usecase.upload.url.CreatePresignedUrl;
import org.example.postbin.Post.persistence.s3.usecase.upload.UploadToObjectStorage;
import org.example.postbin.Post.usecase.GenerateHash;
import org.example.postbin.Post.usecase.access.PostPersist;
import org.example.postbin.Post.usecase.dto.PostReaderWithHashDTO;
import org.example.postbin.Post.usecase.mapper.PostReaderWithHashMapper;
import org.example.postbin.Post.usecase.scenarios.SavePostUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class SavePostUseCaseTest {
    @Mock
    private PostPersist postPersist;
    @Mock
    private PostReaderWithHashMapper saveMapper;
    @Mock
    private GenerateHash generateHash;
    @Mock
    private UploadToObjectStorage uploadToCloudStorage;
    @Mock
    private CreatePresignedUrl createPresignedGetUrl;
    @InjectMocks
    private SavePostUseCase savePostUseCase;

    private final LocalDateTime now = LocalDateTime.now();

    private Post post;

    @BeforeEach
    public void setUp() {
        post = Post.builder()
                .id(1L)
                .title(new Title("Test Title"))
                .hashUrl("Test Hash Url")
                .author(new Author(null, null, null, null))
                .description("Test Description")
                .deletedAfterSomeDays(now.plusDays(1))
                .createdAt(now)
                .build();
    }

    @Test
    public void test_execute_save_returns_one() {
        PostReaderWithHashDTO expectedDto = new PostReaderWithHashDTO(
                post.getTitle().title(),
                post.getHashUrl(),
                post.getUrl(),
                post.getAuthor().nickname(),
                post.getCreatedAt()
        );

        Mockito.when(postPersist.save(any(Post.class))).thenReturn(Optional.of(post));
        Mockito.when(saveMapper.map(post)).thenReturn(expectedDto);
        Mockito.when(generateHash.execute()).thenReturn(post.getHashUrl());
        Mockito.doNothing().when(uploadToCloudStorage).execute(any(Post.class));
        Mockito.when(createPresignedGetUrl.execute(any(String.class), any(Long.class))).thenReturn("url");

        Optional<PostReaderWithHashDTO> result = savePostUseCase.execute(post);

        assertTrue(result.isPresent());
        assertEquals(expectedDto, result.get());
        assertNotNull(post.getCreatedAt());
        assertNotNull(post.getHashUrl());
    }

    @Test
    public void test_execute_save_returns_empty() {
        Mockito.when(postPersist.save(any(Post.class))).thenReturn(Optional.empty());
        Mockito.when(generateHash.execute()).thenReturn(post.getHashUrl());

        Optional<PostReaderWithHashDTO> result = savePostUseCase.execute(null);

        assertFalse(result.isPresent());
    }
}
