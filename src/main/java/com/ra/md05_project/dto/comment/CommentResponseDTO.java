package com.ra.md05_project.dto.comment;

import com.ra.md05_project.model.entity.ver1.Movie;
import com.ra.md05_project.model.entity.ver1.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponseDTO {
    private Long id;

    private String content;

    private Integer rating;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Long parentCommentId;

    private User user;

    private Movie movie;
}
