package com.ra.md05_project.repository;

import com.ra.md05_project.model.entity.ver1.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CinemaRepository extends JpaRepository<Cinema, Long> {
}
