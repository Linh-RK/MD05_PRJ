package com.ra.md05_project.dto.movie;

import com.ra.md05_project.model.constant.MovieStatus;
import com.ra.md05_project.model.constant.MovieType;
import com.ra.md05_project.model.entity.ver1.Category;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieAddDTO {
    private Long id;

    private String title;

    private String description;

    private Integer duration;

    private LocalDate releaseDate;

    private String language;

    private String ageRating;

    private String caution;

    private String posterUrl;

    private String trailerUrl;

    private String director;

    private String cast;

    private String country;

    private MovieStatus status;

    private MovieType type;

    private List<Category> categories = new ArrayList<>();
}
