package com.ra.md05_project.service.bookingDetail;

import com.ra.md05_project.dto.booking.BookingAddDTO;
import com.ra.md05_project.dto.bookingDetail.BookingDetailAddDTO;
import com.ra.md05_project.model.entity.ver1.Booking;
import com.ra.md05_project.model.entity.ver1.BookingDetail;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.Optional;

public interface BookingDetailService {
    Double calculatePrice(BookingDetail bookingDetail);
    Page<BookingDetail> findAll(String search, Pageable pageable);

    BookingDetail create(@Valid BookingDetailAddDTO bookingDetailAddDTO) throws IOException;

    Optional<BookingDetail> findById(Long id);
}
