package com.ra.md05_project.service.comment;

import com.ra.md05_project.dto.comment.CommentAddDTO;
import com.ra.md05_project.dto.comment.CommentUpdateDTO;
import com.ra.md05_project.model.entity.ver1.Comment;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CommentService {
    Page<Comment> findAll(String search, Pageable pageable);

    void delete(Long id);

    Comment create(@Valid CommentAddDTO comment);

    Comment findById(Long id);

    Comment update(Long id, @Valid CommentUpdateDTO comment);
}
