package org.example.postbin.Post.persistence.db.repository;

import lombok.RequiredArgsConstructor;
import org.example.postbin.Post.domain.Page;
import org.example.postbin.Post.domain.filter.PostFilter;
import org.example.postbin.Post.persistence.db.model.PostData;
import org.example.postbin.Post.persistence.db.utils.QPredicates;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.example.postbin.Post.persistence.db.model.QPostData.postData;

@RequiredArgsConstructor
@Repository
public class FilterPostRepositoryImpl implements FilterPostRepository {
    private final PostRepository repository;
    @Override
    public List<PostData> findByFilter(PostFilter filter, Page page) {
        var predicate = QPredicates.getInstance()
                .add(filter.title(), postData.title::containsIgnoreCase)
                .add(filter.nickname(), postData.author.nickname::containsIgnoreCase)
                .buildAnd();

        Pageable pageable = Pageable.ofSize(page.getSize()).withPage(page.getPage());

        return repository.findAll(predicate, pageable).stream()
                .toList();
    }
    @Override
    public Long count(PostFilter filter){
        var predicate = QPredicates.getInstance()
                .add(filter.title(), postData.title::containsIgnoreCase)
                .buildAnd();

        return repository.count(predicate);
    }
}
