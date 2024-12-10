package com.ra.md05_project.repository;

import com.ra.md05_project.model.entity.ver1.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
}
