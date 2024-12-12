package com.ra.md05_project.dto.showtime;

import com.ra.md05_project.model.constant.MovieType;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ShowTimeAddDTO {

    @NotNull(message = "Movie ID is required")
    private Long movieId;

    @NotNull(message = "Room ID is required")
    private Long roomId;

    @NotNull(message = "Start time is required")
    private LocalDateTime startTime;

    @NotNull(message = "Movie type is required")
    private MovieType type;

}
