package com.ra.md05_project.model.entity.ver1;
import com.ra.md05_project.model.constant.MovieType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShowTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") 
    private Long id;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false) // Đặt tên cho khóa ngoại liên kết với Movie
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false) // Đặt tên cho khóa ngoại liên kết với Room
    private Room room;

    @Column(name = "start_time", nullable = false) 
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDate createdAt;

    @Column(name = "update_at")
    private LocalDate updatedAt;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private MovieType type;

    @PrePersist
    @PreUpdate
    public void calculateEndTime() {
        if (movie != null && startTime != null) {
            this.endTime = startTime.plusMinutes(movie.getDuration());
        }
    }
}


