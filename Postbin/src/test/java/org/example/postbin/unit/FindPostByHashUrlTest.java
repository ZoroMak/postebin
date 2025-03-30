package org.example.postbin.unit;

import org.example.postbin.Post.domain.Author;
import org.example.postbin.Post.domain.Post;
import org.example.postbin.Post.domain.Title;
import org.example.postbin.Post.persistence.s3.usecase.download.DownloadFromCloudStorage;
import org.example.postbin.Post.usecase.access.PostExtractor;
import org.example.postbin.Post.usecase.dto.PostReaderDTO;
import org.example.postbin.Post.usecase.mapper.PostReaderMapper;
import org.example.postbin.Post.usecase.scenarios.FindPostByHashUrlUseCase;
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
public class FindPostByHashUrlTest {
    @Mock
    private PostExtractor postExtractor;
    @Mock
    private PostReaderMapper postReaderMapper;
    @Mock
    private DownloadFromCloudStorage downloadFromCloudStorage;
    @InjectMocks
    private FindPostByHashUrlUseCase findPostByHashUrlUseCase;
    private Post post;
    private final LocalDateTime now = LocalDateTime.now();

    @BeforeEach
    public void setUp() {
        post = Post.builder()
                .id(1L)
                .title(new Title("Test Title"))
                .hashUrl("Test Hash Url")
                .author(new Author(1L, "Test", "Testovich", "nickname"))
                .description("Test Description")
                .deletedAfterSomeDays(now.plusDays(1))
                .createdAt(now)
                .build();
    }

    @Test
    public void test_execute_find_post_by_hash_url_and_returns_one() {
        PostReaderDTO postReaderDTO = new PostReaderDTO(
                post.getId(),
                post.getTitle().title(),
                post.getDescription(),
                post.getAuthor().nickname(),
                post.getCreatedAt()
        );

        Mockito.when(postExtractor.getPostByHashUrl(Mockito.anyString())).thenReturn(Optional.of(post));
        Mockito.when(postReaderMapper.map(post)).thenReturn(postReaderDTO);
        Mockito.when(downloadFromCloudStorage.execute(any(String.class))).thenReturn("Test Description");

        Optional<PostReaderDTO> result = findPostByHashUrlUseCase.execute("Test Hash Url");

        assertTrue(result.isPresent());
        assertEquals(postReaderDTO, result.get());
        assertNotNull(post.getCreatedAt());
    }

    @Test
    public void test_execute_find_post_by_hash_url_and_returns_empty() {
        Mockito.when(postExtractor.getPostByHashUrl(Mockito.anyString())).thenReturn(Optional.empty());

        Optional<PostReaderDTO> result = findPostByHashUrlUseCase.execute("");

        assertFalse(result.isPresent());
    }
}
