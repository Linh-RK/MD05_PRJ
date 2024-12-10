package com.ra.md05_project.repository;

import com.ra.md05_project.model.entity.ver1.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
