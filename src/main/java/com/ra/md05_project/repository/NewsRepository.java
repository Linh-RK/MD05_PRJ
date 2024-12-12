package com.ra.md05_project.repository;

import com.ra.md05_project.model.entity.ver1.Festival;
import com.ra.md05_project.model.entity.ver1.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    Page<News> findAllByIsDeletedIsFalse(Pageable pageable);

    Page<News> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseAndIsDeletedIsFalse(String search, String search1, Pageable pageable);
}
