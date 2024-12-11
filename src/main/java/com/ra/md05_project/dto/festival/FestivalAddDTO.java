package com.ra.md05_project.dto.festival;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FestivalAddDTO {
    private Integer id;

    private String title;

    private String image;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
}
