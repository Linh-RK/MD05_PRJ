package com.ra.md05_project.repository;

import com.ra.md05_project.model.entity.ver1.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);

    void deleteByToken(String token);

    void deleteByExpiredAtBefore(LocalDateTime now);
}
