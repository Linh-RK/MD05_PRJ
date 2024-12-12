package com.ra.md05_project.dto.cinema;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CinemaUpdateDTO {
    private Long id;

    private String name;

    private String address;

    private String phoneNumber;

    private LocalDate createdDate;

    private LocalDate updatedDate;
}
