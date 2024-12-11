package com.ra.md05_project.service.movie;

import com.ra.md05_project.dto.movie.MovieAddDTO;
import com.ra.md05_project.dto.movie.MovieUpdateDTO;
import com.ra.md05_project.model.entity.ver1.Movie;
import com.ra.md05_project.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public Page<Movie> findAll(String search, Pageable pageable) {
        if (search == null || search.isBlank()) {
            return movieRepository.findAll(pageable);
        }
        return movieRepository.findByTitleContainingIgnoreCase(search, pageable);
    }

    @Override
    public void delete(Long id) {
        Movie movie = movieRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Movie with id " + id + " not found"));
        movie.setIsDeleted(true);
        movieRepository.save(movie); // Soft delete
    }

    @Override
    public Movie create(MovieAddDTO movieAddDTO) {
        Movie movie = mapToEntity(movieAddDTO);
        validateMovie(movie);
        movie.setIsDeleted(false);
        return movieRepository.save(movie);
    }

    @Override
    public Movie findById(Long id) {
        return movieRepository.findById(id).orElseThrow(()-> new NoSuchElementException("Movie with id " + id + " not found"));
    }

    @Override
    public Movie update(Long id, MovieUpdateDTO movieUpdateDTO) {
        Movie existingMovie = movieRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Movie with id " + id + " not found"));

        Movie updatedMovie = mapToEntity(movieUpdateDTO);
        validateMovie(updatedMovie);

        existingMovie = Movie.builder()
                .id(existingMovie.getId()) // Preserve ID
                .title(updatedMovie.getTitle())
                .description(updatedMovie.getDescription())
                .duration(updatedMovie.getDuration())
                .releaseDate(updatedMovie.getReleaseDate())
                .language(updatedMovie.getLanguage())
                .ageRating(updatedMovie.getAgeRating())
                .caution(updatedMovie.getCaution())
                .posterUrl(updatedMovie.getPosterUrl())
                .trailerUrl(updatedMovie.getTrailerUrl())
                .director(updatedMovie.getDirector())
                .cast(updatedMovie.getCast())
                .country(updatedMovie.getCountry())
                .status(updatedMovie.getStatus())
                .type(updatedMovie.getType())
                .categories(updatedMovie.getCategories())
                .isDeleted(existingMovie.getIsDeleted())
                .build();

        return movieRepository.save(existingMovie);
    }

    private void validateMovie(Movie movie) {
        if (movie.getReleaseDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Release date cannot be in the future");
        }
        if (movie.getDuration() <= 0) {
            throw new IllegalArgumentException("Duration must be a positive value");
        }
    }

    private Movie mapToEntity(MovieAddDTO dto) {
        return Movie.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .duration(dto.getDuration())
                .releaseDate(dto.getReleaseDate())
                .language(dto.getLanguage())
                .ageRating(dto.getAgeRating())
                .caution(dto.getCaution())
                .posterUrl(dto.getPosterUrl())
                .trailerUrl(dto.getTrailerUrl())
                .director(dto.getDirector())
                .cast(dto.getCast())
                .country(dto.getCountry())
                .status(dto.getStatus())
                .type(dto.getType())
                .categories(dto.getCategories())
                .build();
    }

    private Movie mapToEntity(MovieUpdateDTO dto) {
        return Movie.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .duration(dto.getDuration())
                .releaseDate(dto.getReleaseDate())
                .language(dto.getLanguage())
                .ageRating(dto.getAgeRating())
                .caution(dto.getCaution())
                .posterUrl(dto.getPosterUrl())
                .trailerUrl(dto.getTrailerUrl())
                .director(dto.getDirector())
                .cast(dto.getCast())
                .country(dto.getCountry())
                .status(dto.getStatus())
                .type(dto.getType())
                .categories(dto.getCategories())
                .build();
    }
}
