package com.ra.md05_project.repository;

import com.ra.md05_project.model.entity.ver1.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    Page<Room> findByRoomNameContainingIgnoreCaseOrStatusContainingIgnoreCase(String search, String search1, Pageable pageable);
}
