package org.example.postbin.unit;

import org.example.postbin.Post.domain.Author;
import org.example.postbin.Post.domain.Page;
import org.example.postbin.Post.domain.Post;
import org.example.postbin.Post.domain.Title;
import org.example.postbin.Post.domain.filter.PostFilter;
import org.example.postbin.Post.usecase.access.PostExtractor;
import org.example.postbin.Post.usecase.dto.ListPostReaderDTO;
import org.example.postbin.Post.usecase.dto.PostReaderDTO;
import org.example.postbin.Post.usecase.mapper.ListPostReaderMapper;
import org.example.postbin.Post.usecase.mapper.PostReaderMapper;
import org.example.postbin.Post.usecase.scenarios.FindPostByFilterUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class FindPostByAuthorNicknameTest {
    @Mock
    private PostExtractor postExtractor;
    @Mock
    private ListPostReaderMapper postReaderMapper;
    @InjectMocks
    private FindPostByFilterUseCase findPostByFilterUseCase;
    private final Page page = new Page(0, 1);
    private final PostFilter filter = new PostFilter("", "Test nickname");
    @Test
    public void test_find_post_by_author_nickname_and_return_one_object() {
        LocalDateTime now = LocalDateTime.now();

        Post post = Post.builder()
                .title(new Title("Test Title"))
                .hashUrl("Test Hash Url")
                .author(new Author(1L, "Test", "Testovich", "nickname"))
                .deletedAfterSomeDays(now.plusDays(1))
                .createdAt(now)
                .build();

        ListPostReaderDTO postReaderDTO = new ListPostReaderDTO(
                post.getId(),
                post.getTitle().title(),
                post.getHashUrl(),
                post.getAuthor().nickname(),
                post.getCreatedAt()
        );



        List<Post> posts = List.of(post);

        Mockito.when(postExtractor.findPostByFilter(any(PostFilter.class), any(Page.class))).thenReturn(posts);
        Mockito.when(postReaderMapper.map(post)).thenReturn(postReaderDTO);

        List<ListPostReaderDTO> result = findPostByFilterUseCase.execute(filter, page);

        Mockito.verify(postExtractor, Mockito.times(1)).findPostByFilter(any(PostFilter.class), any(Page.class));

        assertEquals(1, result.size());
        assertEquals("Test Title", result.get(0).getTitle());
        assertEquals("nickname", result.get(0).getAuthorNickname());
        assertEquals(now, result.get(0).getCreatedAt());
    }

    @Test
    public void test_find_post_by_title_and_return_empty_list() {
        List<Post> posts = new ArrayList<>();

        Mockito.when(postExtractor.findPostByFilter(any(PostFilter.class), any(Page.class))).thenReturn(posts);

        List<ListPostReaderDTO> result = findPostByFilterUseCase.execute(filter, page);

        Mockito.verify(postExtractor, Mockito.times(1)).findPostByFilter(any(PostFilter.class), any(Page.class));

        assertEquals(0, result.size());
    }

    @Test
    public void test_find_post_by_title_and_return_many_objects() {
        LocalDateTime now = LocalDateTime.now();

        Post post1 = Post.builder()
                .id(1L)
                .title(new Title("Test Title"))
                .hashUrl("Test Hash Url")
                .author(new Author(1L, "Test", "Testovich", "nickname"))
                .deletedAfterSomeDays(now.plusDays(1))
                .createdAt(now)
                .build();

        Post post2 = Post.builder()
                .id(2L)
                .title(new Title("Test Title"))
                .hashUrl("Test Hash Url")
                .author(new Author(1L, "Test", "Testovich", "nickname"))
                .deletedAfterSomeDays(now.plusDays(1))
                .createdAt(now)
                .build();

        ListPostReaderDTO postReaderDTO1 = new ListPostReaderDTO(
                post1.getId(),
                post1.getTitle().title(),
                post1.getHashUrl(),
                post1.getAuthor().nickname(),
                post1.getCreatedAt()
        );

        ListPostReaderDTO postReaderDTO2 = new ListPostReaderDTO(
                post2.getId(),
                post2.getTitle().title(),
                post1.getHashUrl(),
                post2.getAuthor().nickname(),
                post2.getCreatedAt()
        );

        List<Post> posts = List.of(post1, post2);
        List<ListPostReaderDTO> expectedResult = List.of(postReaderDTO1, postReaderDTO2);

        Mockito.when(postExtractor.findPostByFilter(any(PostFilter.class), any(Page.class))).thenReturn(posts);
        Mockito.when(postReaderMapper.map(any(Post.class))).thenAnswer(invocation -> {
            Post post = invocation.getArgument(0);
            return new ListPostReaderDTO(
                    post.getId(),
                    post.getTitle().title(),
                    post.getHashUrl(),
                    post.getAuthor().nickname(),
                    post.getCreatedAt()
            );
        });

        List<ListPostReaderDTO> result = findPostByFilterUseCase.execute(filter, page);

        Mockito.verify(postExtractor, Mockito.times(1)).findPostByFilter(any(PostFilter.class), any(Page.class));

        assertEquals(expectedResult.size(), result.size());
        for (int i = 0; i < expectedResult.size(); i++) {
            assertEquals(expectedResult.get(i).getTitle(), result.get(i).getTitle());
            assertEquals(expectedResult.get(i).getAuthorNickname(), result.get(i).getAuthorNickname());
            assertEquals(expectedResult.get(i).getCreatedAt(), result.get(i).getCreatedAt());
        }
    }
}
