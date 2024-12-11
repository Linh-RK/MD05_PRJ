package com.ra.md05_project.repository;

import com.ra.md05_project.model.entity.ver1.Category;
import com.ra.md05_project.model.entity.ver1.Cinema;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Long> {

    Page<Cinema> findByNameContainingIgnoreCaseAndIsDeletedFalse(String search, Pageable pageable);

    Optional<Cinema> findByIdAndIsDeletedFalse(Long id);
}
