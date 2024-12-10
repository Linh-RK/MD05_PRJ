package com.ra.md05_project.repository;

import com.ra.md05_project.model.entity.ver1.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
