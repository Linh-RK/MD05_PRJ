package com.ra.md05_project.repository;

import com.ra.md05_project.model.entity.ver1.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowTimeRepository extends JpaRepository<Showtime,Long> {
}
