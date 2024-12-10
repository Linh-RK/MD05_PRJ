package com.ra.md05_project.repository;

import com.ra.md05_project.model.entity.ver1.Festival;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FestivalRepositoty extends JpaRepository<Festival, Long> {
}
