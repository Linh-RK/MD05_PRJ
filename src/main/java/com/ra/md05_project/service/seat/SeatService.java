package com.ra.md05_project.service.seat;

import com.ra.md05_project.dto.seat.SeatAddDTO;
import com.ra.md05_project.dto.seat.SeatResponseDTO;
import com.ra.md05_project.dto.seat.SeatUpdateDTO;
import com.ra.md05_project.model.entity.ver1.Seat;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface SeatService {
    Page<SeatResponseDTO> findAll(String search, Pageable pageable);

    void delete(Long id);

    SeatResponseDTO create(@Valid SeatAddDTO seat);

    SeatResponseDTO findById(Long id);

    SeatResponseDTO update(Long id, @Valid SeatUpdateDTO seat);
}
