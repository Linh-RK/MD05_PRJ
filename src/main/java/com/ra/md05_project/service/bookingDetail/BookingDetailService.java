package com.ra.md05_project.service.bookingDetail;

import com.ra.md05_project.dto.bookingDetail.BookingDetailResponseDTO;
import com.ra.md05_project.model.entity.ver1.BookingDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BookingDetailService {
    Page<BookingDetailResponseDTO> findAll(String search, Pageable pageable);

    BookingDetailResponseDTO findById(Long id);
}
