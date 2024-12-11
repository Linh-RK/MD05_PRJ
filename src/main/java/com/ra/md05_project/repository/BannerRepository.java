package com.ra.md05_project.repository;

import com.ra.md05_project.model.entity.ver1.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannerRepository extends JpaRepository<Banner, Long> {
    Page<Banner> findAllByBannerNameContainingIgnoreCase(String search, Pageable pageable);
}
