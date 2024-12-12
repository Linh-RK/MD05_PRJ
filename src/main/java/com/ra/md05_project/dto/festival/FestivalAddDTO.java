package com.ra.md05_project.dto.festival;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FestivalAddDTO {
    @NotBlank(message = "Title is mandatory")
    private String title;

    @NotNull(message = "Please choose image")
    private MultipartFile image;

    @NotNull(message = "Start time is mandatory")
    private LocalDate startTime;

    @NotNull(message = "End time is mandatory")
    private LocalDate endTime;
}
