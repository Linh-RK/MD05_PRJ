package com.ra.md05_project.model.entity.ver1;

import com.ra.md05_project.model.constant.MovieGenre;
import com.ra.md05_project.model.constant.MovieStatus;
import com.ra.md05_project.model.constant.MovieType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") 
    private Long id;

    @Column(name = "title", length = 255, nullable = false) 
    private String title;

    @Column(name = "description", columnDefinition = "TEXT") 
    private String description;

    @Column(name = "duration") 
    private Integer duration;

    @Column(name = "release_date", nullable = false) 
    private LocalDate releaseDate;

    @Column(name = "language", length = 50) 
    private String language;

    @Column(name = "age_rating", length = 50) 
    private String ageRating;

    @Column(name = "caution", columnDefinition = "TEXT") 
    private String caution;

    @Column(name = "poster_url", length = 255) 
    private String posterUrl;

    @Column(name = "director", columnDefinition = "TEXT") 
    private String director;

    @ElementCollection
    @CollectionTable(name = "movie_cast",
            joinColumns = @JoinColumn(name = "movie_id")) // Định nghĩa bảng phụ cho danh sách diễn viên
    @Column(name = "cast_member") 
    private List<String> cast;

    @Column(name = "country", columnDefinition = "TEXT") 
    private String country;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false) 
    private MovieStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "genre", nullable = false) 
    private MovieGenre genre;
}
