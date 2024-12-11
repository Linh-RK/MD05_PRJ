package com.ra.md05_project.service.comment;

import com.ra.md05_project.dto.comment.CommentAddDTO;
import com.ra.md05_project.dto.comment.CommentUpdateDTO;
import com.ra.md05_project.model.entity.ver1.Comment;
import com.ra.md05_project.model.entity.ver1.Movie;
import com.ra.md05_project.model.entity.ver1.User;
import com.ra.md05_project.repository.CommentRepository;
import com.ra.md05_project.repository.MovieRepository;
import com.ra.md05_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public Page<Comment> findAll(String search, Pageable pageable) {
        if (search == null || search.isBlank()) {
            return commentRepository.findAll(pageable);
        }
        return commentRepository.findAllByContentContainingIgnoreCase(search, pageable);
    }

    @Override
    public void delete(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Comment with id " + id + " not found"));
        comment.setIsDeleted(true);
        commentRepository.save(comment); // Soft delete by marking as deleted
    }

    @Override
    public Comment create(CommentAddDTO commentAddDTO) {
        // Validate user
        User user = userRepository.findById(commentAddDTO.getId())
                .orElseThrow(() -> new NoSuchElementException("User with id " + commentAddDTO.getId() + " not found"));

        // Validate movie
        Movie movie = movieRepository.findById(commentAddDTO.getId())
                .orElseThrow(() -> new NoSuchElementException("Movie with id " + commentAddDTO.getId() + " not found"));

        // Validate parent comment (if provided)
        Comment parentComment = null;
        if (commentAddDTO.getParentCommentId() != null) {
            parentComment = commentRepository.findById(commentAddDTO.getParentCommentId())
                    .orElseThrow(() -> new NoSuchElementException("Parent comment with id " + commentAddDTO.getParentCommentId() + " not found"));
        }

        // Create new comment
        Comment newComment = Comment.builder()
                .content(commentAddDTO.getContent())
                .rating(commentAddDTO.getRating())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .parentCommentId(parentComment != null ? parentComment.getId() : null)
                .isDeleted(false)
                .user(user)
                .movie(movie)
                .build();

        return commentRepository.save(newComment);
    }

    @Override
    public Comment findById(Long id) {
        return commentRepository.findById(id).orElseThrow(()-> new NoSuchElementException("Comment with id " + id + " not found"));
    }

    @Override
    public Comment update(Long id, CommentUpdateDTO commentUpdateDTO) {
        Comment existingComment = commentRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Comment with id " + id + " not found"));

        // Update fields
        existingComment.setContent(commentUpdateDTO.getContent());
        existingComment.setRating(commentUpdateDTO.getRating());
        existingComment.setUpdatedAt(LocalDateTime.now());

        return commentRepository.save(existingComment);
    }
}

