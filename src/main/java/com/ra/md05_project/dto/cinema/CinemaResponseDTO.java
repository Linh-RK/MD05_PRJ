package com.ra.md05_project.dto.cinema;

import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CinemaResponseDTO {
    private Long id;

    private String name;

    private String address;

    private String phoneNumber;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;
}
