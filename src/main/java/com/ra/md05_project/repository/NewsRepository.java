package com.ra.md05_project.repository;

import com.ra.md05_project.model.entity.ver1.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
}
