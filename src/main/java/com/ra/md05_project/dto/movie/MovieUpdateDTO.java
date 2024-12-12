package com.ra.md05_project.dto.movie;

import com.ra.md05_project.model.constant.MovieStatus;
import com.ra.md05_project.model.constant.MovieType;
import com.ra.md05_project.model.entity.ver1.Category;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieUpdateDTO {
    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title cannot exceed 255 characters")
    private String title;

    @Size(max = 5000, message = "Description cannot exceed 5000 characters")
    private String description;

    @NotNull(message = "Duration is required")
    @Positive(message = "Duration must be greater than 0")
    private Integer duration;

    @NotNull(message = "Release date is required")
    @PastOrPresent(message = "Release date cannot be in the future")
    private LocalDate releaseDate;

    @NotBlank(message = "Language is required")
    private String language;

    @NotBlank(message = "Age rating is required")
    private String ageRating;

    private MultipartFile posterUrl;

    private MultipartFile trailerUrl;

    private String director;

    private String cast;

    private String country;

    @NotNull(message = "Status is required")
    private MovieStatus status;

    @NotNull(message = "Type is required")
    private MovieType type;

    private List<Long> categoryIds;
}
