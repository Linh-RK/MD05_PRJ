package com.ra.md05_project.repository;

import com.ra.md05_project.model.entity.ver1.ShowTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowTimeRepository extends JpaRepository<ShowTime,Long> {


    Page<ShowTime> findAllByIsDeletedIsFalse(Pageable pageable);

    Page<ShowTime> findByMovie_TitleContainingIgnoreCaseOrRoom_RoomNameContainingIgnoreCaseAndIsDeletedIsFalse(String search, String search1, Pageable pageable);
}
