package com.ra.md05_project.repository;

import com.ra.md05_project.model.constant.BannerType;
import com.ra.md05_project.model.entity.ver1.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BannerRepository extends JpaRepository<Banner, Long> {
    @Query("SELECT b FROM Banner b WHERE " +
            "LOWER(b.bannerName) LIKE LOWER(CONCAT('%', :bannerName, '%')) " +
            "AND (:type IS NULL OR b.type = :type) " +
            "AND (:position IS NULL OR b.position = :position)")
    Page<Banner> findAllWithFilters(
            @Param("bannerName") String bannerName,
            @Param("type") BannerType type,
            @Param("position") String position,
            Pageable pageable
    );
}
