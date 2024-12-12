package com.ra.md05_project.controller.admin;

import com.ra.md05_project.dto.movie.MovieAddDTO;
import com.ra.md05_project.dto.movie.MovieResponseDTO;
import com.ra.md05_project.dto.movie.MovieUpdateDTO;
import com.ra.md05_project.dto.user.ResponseDTOSuccess;
import com.ra.md05_project.model.entity.ver1.Festival;
import com.ra.md05_project.model.entity.ver1.Movie;
import com.ra.md05_project.service.festival.FestivalService;
import com.ra.md05_project.service.movie.MovieService;
import com.ra.md05_project.service.movie.MovieServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/admin/movie")
public class ADMovieController {
    @Autowired
    private MovieService movieService;

    @GetMapping
    public ResponseEntity<Page<MovieResponseDTO>> findAllMovie(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "3") int size,
            @RequestParam(name = "search", defaultValue = "") String search,
            @RequestParam(name = "sort", defaultValue = "id") String sort,
            @RequestParam(name = "direction", defaultValue = "asc") String direction
    ) {
        Sort sortOrder = Sort.by(sort);
        sortOrder = direction.equalsIgnoreCase("desc") ? sortOrder.descending() : sortOrder.ascending();
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        Page<Movie> movies = movieService.findAll(search ,pageable );
        // Map to MovieResponseDTO
        Page<MovieResponseDTO> responseDTOs = movies.map(movieService::mapToResponseDTO);
        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponseDTO> getMovieById(@PathVariable Long id) {
        Movie movie = movieService.findById(id);
        return new ResponseEntity<>(movieService.mapToResponseDTO(movie), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MovieResponseDTO> createMovie(@Valid @ModelAttribute MovieAddDTO movieAddDTO) throws IOException {
        Movie movie = movieService.create(movieAddDTO);
        return  new ResponseEntity<>(movieService.mapToResponseDTO(movie), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieResponseDTO> updateMovie(@PathVariable Long id,@Valid @ModelAttribute MovieUpdateDTO movieUpdateDTO) throws IOException {
        Movie movie = movieService.update(id, movieUpdateDTO);
            return new ResponseEntity<>(movieService.mapToResponseDTO(movie), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable Long id) {
            movieService.delete(id);
            return new ResponseEntity<>(new ResponseDTOSuccess<>("Delete successfully",200),HttpStatus.OK);
    }
}
