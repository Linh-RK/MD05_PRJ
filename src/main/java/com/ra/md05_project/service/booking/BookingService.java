package com.ra.md05_project.service.booking;


import com.ra.md05_project.dto.booking.BookingAddDTO;
import com.ra.md05_project.model.entity.ver1.Booking;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface BookingService {
    Page<Booking> findAll(String search, Pageable pageable);

    Booking create(@Valid BookingAddDTO bookingAddDTO) throws IOException;

    Booking findById(Long id);

    void delete(Long id);
}
