package org.example.postbin.Post.persistence.db.repository;

import org.example.postbin.Post.persistence.db.model.PostData;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Repository
public interface PostRepository extends JpaRepository<PostData, Long>, QuerydslPredicateExecutor<PostData> {
    Optional<PostData> findByHashUrl(String hashUrl);
}