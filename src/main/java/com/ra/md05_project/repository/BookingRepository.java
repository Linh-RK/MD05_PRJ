package com.ra.md05_project.repository;

import com.ra.md05_project.model.entity.ver1.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}