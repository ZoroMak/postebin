package org.example.postbin.Post.persistence.db;

import lombok.RequiredArgsConstructor;
import org.example.postbin.Post.domain.Page;
import org.example.postbin.Post.domain.Post;
import org.example.postbin.Post.domain.filter.PostFilter;
import org.example.postbin.Post.persistence.db.mapper.FromDBPostMapper;
import org.example.postbin.Post.persistence.db.mapper.FromServicePostMapper;
import org.example.postbin.Post.persistence.db.model.AuthorData;
import org.example.postbin.Post.persistence.db.model.PostData;
import org.example.postbin.Post.persistence.db.repository.AuthorRepository;
import org.example.postbin.Post.persistence.db.repository.FilterPostRepository;
import org.example.postbin.Post.persistence.db.repository.PostRepository;
import org.example.postbin.Post.usecase.access.PostExtractor;
import org.example.postbin.Post.usecase.access.PostPersist;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MainFunctionalUseCaseRepository implements PostExtractor, PostPersist {
    private final PostRepository postRepository;
    private final FromDBPostMapper fromDBPostMapper;
    private final FromServicePostMapper fromServicePostMapper;
    private final FilterPostRepository postFilter;
    private final AuthorRepository authorRepository;

    @Override
    @Cacheable(value = "posts", key = "#hashUrl", unless = "#result == null")
    public Optional<Post> getPostByHashUrl(String hashUrl) {
        return postRepository.findByHashUrl(hashUrl)
                .map(fromDBPostMapper::map);
    }

    @Override
    public List<Post> findPostByFilter(PostFilter filter, Page page) {
        return postFilter.findByFilter(filter, page).stream()
                .map(fromDBPostMapper::map)
                .toList();
    }

    @Override
    public Long count(PostFilter filter) {
        return postFilter.count(filter);
    }

    @Override
    @CachePut(value = "posts", key = "#post.hashUrl")
    public Optional<Post> save(Post post) {
        PostData postData = fromServicePostMapper.map(post);
        postData.setAuthor(getAuthor(postData.getAuthor()));
        postRepository.save(postData);

        return Optional.of(fromDBPostMapper.map(postData));
    }

    private AuthorData getAuthor(AuthorData author) {
        Optional<AuthorData> authorData = authorRepository.findByNickname(author.getNickname());

        return authorData.orElseGet(() -> authorRepository.save(author));

    }
}
