package org.example.postbin.Post.persistence.db.repository;

import org.example.postbin.Post.persistence.db.model.AuthorData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorData, Long> {
    Optional<AuthorData> findByNickname(String nickname);
}
