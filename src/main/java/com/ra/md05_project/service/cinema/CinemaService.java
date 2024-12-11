package com.ra.md05_project.service.cinema;

import com.ra.md05_project.dto.cinema.CinemaAddDTO;
import com.ra.md05_project.dto.cinema.CinemaUpdateDTO;
import com.ra.md05_project.model.entity.ver1.Cinema;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CinemaService {
    Page<Cinema> findAll(String search, Pageable pageable);

    void delete(Long id);

    Cinema create(@Valid CinemaAddDTO cinema);

    Cinema findById(Long id);

    Cinema update(Long id, @Valid CinemaUpdateDTO cinema);
}
