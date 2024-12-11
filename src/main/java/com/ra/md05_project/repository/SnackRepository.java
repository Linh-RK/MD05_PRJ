package com.ra.md05_project.repository;

import com.ra.md05_project.model.entity.ver1.Snack;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SnackRepository extends JpaRepository<Snack, Long> {
    Page<Snack> findByNameContainingIgnoreCaseOrTypeContainingIgnoreCase(String search, String search1, Pageable pageable);
}
