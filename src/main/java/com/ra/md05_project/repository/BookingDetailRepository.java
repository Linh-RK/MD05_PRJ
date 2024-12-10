package com.ra.md05_project.repository;

import com.ra.md05_project.model.entity.ver1.BookingDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingDetailRepository extends JpaRepository<BookingDetail, Integer> {
}
