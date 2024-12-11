package com.ra.md05_project.service.seat;

import com.ra.md05_project.dto.seat.SeatAddDTO;
import com.ra.md05_project.dto.seat.SeatUpdateDTO;
import com.ra.md05_project.model.entity.ver1.Seat;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface SeatService {
    Page<Seat> findAll(String search, Pageable pageable);

    void delete(Long id);

    Seat create(@Valid SeatAddDTO seat);

    Seat findById(Long id);

    Seat update(Long id, @Valid SeatUpdateDTO seat);
}
