package com.ra.md05_project.service.booking;


import com.ra.md05_project.dto.booking.BookingAddDTO;
import com.ra.md05_project.dto.booking.BookingResponseDTO;
import com.ra.md05_project.model.entity.ver1.User;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface BookingService {
    Page<BookingResponseDTO> findAll(String search, Pageable pageable);

    BookingResponseDTO create(@Valid BookingAddDTO bookingAddDTO, User user) throws IOException;

    BookingResponseDTO findById(Long id);

    void delete(Long id);
}
