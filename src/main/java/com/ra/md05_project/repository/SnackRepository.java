package com.ra.md05_project.repository;

import com.ra.md05_project.model.entity.ver1.Snack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SnackRepository extends JpaRepository<Snack, Long> {
}
