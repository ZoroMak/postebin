package org.example.postbin.Post.persistence.db.repository;

import org.example.postbin.Post.domain.Page;
import org.example.postbin.Post.domain.filter.PostFilter;
import org.example.postbin.Post.persistence.db.model.PostData;
import org.example.postbin.Post.persistence.db.utils.QPredicates;

import java.util.List;

import static org.example.postbin.Post.persistence.db.model.QPostData.postData;

public interface FilterPostRepository {
    List<PostData> findByFilter(PostFilter filter, Page page);
    Long count(PostFilter filter);
}
