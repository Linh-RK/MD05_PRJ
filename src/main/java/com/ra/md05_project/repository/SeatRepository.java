package com.ra.md05_project.repository;

import com.ra.md05_project.model.entity.ver1.Festival;
import com.ra.md05_project.model.entity.ver1.Seat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    Page<Seat> findAllByIsDeletedIsFalse(Pageable pageable);

    Page<Seat> findByRoom_IdAndIsDeletedIsFalse( Long roomId, Pageable pageable);

    void deleteAllByRoom_Id(Long id);
}
