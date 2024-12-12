package com.ra.md05_project.dto.showtime;

import com.ra.md05_project.model.constant.MovieType;
import com.ra.md05_project.model.entity.ver1.Movie;
import com.ra.md05_project.model.entity.ver1.Room;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShowTimeResponseDTO {
    private Long id;

    private Long movieId;

    private Long roomId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    private Boolean isDeleted;

    private MovieType type;
}
