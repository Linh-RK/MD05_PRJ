package com.ra.md05_project.service.movie;

import com.ra.md05_project.dto.movie.MovieAddDTO;
import com.ra.md05_project.dto.movie.MovieResponseDTO;
import com.ra.md05_project.dto.movie.MovieUpdateDTO;
import com.ra.md05_project.model.entity.ver1.Category;
import com.ra.md05_project.model.entity.ver1.Movie;
import com.ra.md05_project.repository.CategoryRepository;
import com.ra.md05_project.repository.MovieRepository;
import com.ra.md05_project.service.uploadFile.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UploadService uploadService;

    @Override
    public Page<Movie> findAll(String search, Pageable pageable) {
        return (search == null || search.isBlank())
                ? movieRepository.findAll(pageable)
                : movieRepository.findByTitleContainingIgnoreCase(search, pageable);
    }

    @Override
    public void delete(Long id) {
        Movie movie = findMovieById(id);
        movie.setIsDeleted(true);
        movieRepository.save(movie);
    }

    @Override
    public Movie create(MovieAddDTO dto) throws IOException {
        Movie movie = mapToEntity(dto);
        validateMovie(movie);
        movie.setIsDeleted(false);
        return movieRepository.save(movie);
    }

    @Override
    public Movie findById(Long id) {
        return findMovieById(id);
    }

    @Override
    public Movie update(Long id, MovieUpdateDTO dto) throws IOException {
        Movie existingMovie = findMovieById(id);
        validateMovie(existingMovie);

        String posterUrl = (dto.getPosterUrl() != null && !dto.getPosterUrl().isEmpty())
                ? uploadService.uploadFile(dto.getPosterUrl())
                : existingMovie.getPosterUrl();
        String trailerUrl = (dto.getTrailerUrl() != null && !dto.getTrailerUrl().isEmpty())
                ? uploadService.uploadFile(dto.getTrailerUrl())
                : existingMovie.getTrailerUrl();

        Movie movieToSave = mapToEntity(dto);
        movieToSave.setId(existingMovie.getId());
        movieToSave.setPosterUrl(posterUrl);
        movieToSave.setTrailerUrl(trailerUrl);
        movieToSave.setIsDeleted(existingMovie.getIsDeleted());

        return movieRepository.save(movieToSave);
    }

    @Override
    public MovieResponseDTO mapToResponseDTO(Movie movie) {
        return MovieResponseDTO.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .duration(movie.getDuration())
                .releaseDate(movie.getReleaseDate())
                .language(movie.getLanguage())
                .ageRating(movie.getAgeRating())
                .caution(movie.getCaution())
                .posterUrl(movie.getPosterUrl())
                .trailerUrl(movie.getTrailerUrl())
                .director(movie.getDirector())
                .cast(movie.getCast())
                .country(movie.getCountry())
                .status(movie.getStatus())
                .type(movie.getType())
                .categoriesId(movie.getCategories().stream()
                        .map(Category::getId)
                        .collect(Collectors.toList()))
                .build();
    }

    private void validateMovie(Movie dto) {
        if (dto.getReleaseDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Release date cannot be in the future");
        }
        if (dto.getDuration() <= 0) {
            throw new IllegalArgumentException("Duration must be a positive value");
        }
    }

    private Movie mapToEntity(MovieAddDTO dto) throws IOException {
        List<Category> categories = categoryRepository.findAllById(dto.getCategoryIds());
        return Movie.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .duration(dto.getDuration())
                .releaseDate(dto.getReleaseDate())
                .language(dto.getLanguage())
                .ageRating(dto.getAgeRating())
                .posterUrl(uploadService.uploadFile(dto.getPosterUrl()))
                .trailerUrl(uploadService.uploadFile(dto.getTrailerUrl()))
                .director(dto.getDirector())
                .cast(dto.getCast())
                .country(dto.getCountry())
                .status(dto.getStatus())
                .type(dto.getType())
                .categories(categories)
                .build();
    }

    private Movie mapToEntity(MovieUpdateDTO dto) throws IOException {
        List<Category> categories = categoryRepository.findAllById(dto.getCategoryIds());
        return Movie.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .duration(dto.getDuration())
                .releaseDate(dto.getReleaseDate())
                .language(dto.getLanguage())
                .ageRating(dto.getAgeRating())
//                .trailerUrl(dto.getTrailerUrl())
                .director(dto.getDirector())
                .cast(dto.getCast())
                .country(dto.getCountry())
                .status(dto.getStatus())
                .type(dto.getType())
                .categories(categories)
                .build();
    }

    private Movie findMovieById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Movie with id " + id + " not found"));
    }
}
