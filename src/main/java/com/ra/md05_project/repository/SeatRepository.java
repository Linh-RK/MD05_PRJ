package com.ra.md05_project.repository;

import com.ra.md05_project.model.entity.ver1.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Long> {
}
