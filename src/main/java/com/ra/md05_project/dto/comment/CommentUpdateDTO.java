package com.ra.md05_project.dto.comment;

import com.ra.md05_project.model.entity.ver1.Movie;
import com.ra.md05_project.model.entity.ver1.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentUpdateDTO {
    private Long id;

    private String content;

    private Integer rating;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Long parentCommentId;

    private User user;

    private Movie movie;
}
