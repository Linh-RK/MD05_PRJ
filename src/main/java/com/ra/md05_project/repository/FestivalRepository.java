package com.ra.md05_project.repository;

import com.ra.md05_project.model.entity.ver1.Category;
import com.ra.md05_project.model.entity.ver1.Festival;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FestivalRepository extends JpaRepository<Festival, Long> {

    Page<Festival> findByTitleContainingIgnoreCaseAndIsDeletedIsFalse(String search, Pageable pageable);
    Page<Festival> findAllByIsDeletedIsFalse(Pageable pageable);
}
