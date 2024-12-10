package com.ra.md05_project.repository;

import com.ra.md05_project.model.entity.ver1.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
