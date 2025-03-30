package org.example.postbin.integration.repository;

import jakarta.transaction.Transactional;
import org.example.postbin.Post.domain.Page;
import org.example.postbin.Post.domain.filter.PostFilter;
import org.example.postbin.Post.persistence.db.model.PostData;
import org.example.postbin.Post.persistence.db.repository.FilterPostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class FilterPostRepositoryTest {
    @Autowired
    private FilterPostRepository repository;
    private final Page page = new Page(0, 8);

    @Test
    public void test_find_by_filter_without_title() {
        PostFilter filter = new PostFilter(null, "johndoe");

        List<PostData> actual = repository.findByFilter(filter, page);

        assertEquals(1, actual.size());
        PostData postData = actual.get(0);
        assertEquals("Post 1", postData.getTitle());
        assertEquals("12345678", postData.getHashUrl());
        assertEquals("johndoe", postData.getAuthor().getNickname());
    }

    @Test
    public void test_find_by_filter_without_nickname() {
        PostFilter filter = new PostFilter("Post 1", null);

        List<PostData> actual = repository.findByFilter(filter, page);

        assertEquals(1, actual.size());
        PostData postData = actual.get(0);
        assertEquals("Post 1", postData.getTitle());
        assertEquals("12345678", postData.getHashUrl());
        assertEquals("johndoe", postData.getAuthor().getNickname());
    }

    @Test
    public void test_find_by_filter_with_title_and_nickname() {
        PostFilter filter = new PostFilter("Post", "jo");

        List<PostData> actual = repository.findByFilter(filter, page);

        assertEquals(8, actual.size());
//        PostData postData = actual.get(0);
//        assertEquals("Post 1", postData.getTitle());
//        assertEquals("12345678", postData.getHashUrl());
//        assertEquals("johndoe", postData.getAuthor().getNickname());
//
//        postData = actual.get(1);
//        assertEquals("Post 3", postData.getTitle());
//        assertEquals("13579246", postData.getHashUrl());
//        assertEquals("peterjones", postData.getAuthor().getNickname());
    }
}
