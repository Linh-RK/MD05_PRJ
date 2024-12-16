package com.ra.md05_project.repository;

import com.ra.md05_project.model.entity.ver1.BookingDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookingDetailRepository extends JpaRepository<BookingDetail, Long> {
    Page<BookingDetail> findByBooking_Showtime_Movie_Title(String search, Pageable pageable);
}
