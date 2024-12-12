package com.ra.md05_project.service.movie;

import com.ra.md05_project.dto.movie.MovieAddDTO;
import com.ra.md05_project.dto.movie.MovieResponseDTO;
import com.ra.md05_project.dto.movie.MovieUpdateDTO;
import com.ra.md05_project.model.entity.ver1.Movie;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.Optional;

public interface MovieService {
    Page<Movie> findAll(String search, Pageable pageable);

    void delete(Long id);

    Movie create(@Valid MovieAddDTO movieAddDTO) throws IOException;

    Movie findById(Long id);

    Movie update(Long id, MovieUpdateDTO movieUpdateDTO) throws IOException;

    MovieResponseDTO mapToResponseDTO(Movie movie);

}
