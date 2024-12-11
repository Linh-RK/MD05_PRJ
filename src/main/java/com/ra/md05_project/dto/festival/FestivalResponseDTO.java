package com.ra.md05_project.dto.festival;

import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FestivalResponseDTO {
    private Integer id;

    private String title;

    private String image;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
}
