package com.ra.md05_project.model.entity.ver1;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "content", nullable = false, length = 1000)
    private String content;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "parent_comment_id")
    private Long parentCommentId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Khóa ngoại liên kết với User
    private User user;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false) // Khóa ngoại liên kết với Movie
    private Movie movie;
}

