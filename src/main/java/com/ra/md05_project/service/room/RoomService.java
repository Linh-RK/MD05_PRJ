package com.ra.md05_project.service.room;

import com.ra.md05_project.dto.room.RoomAddDTO;
import com.ra.md05_project.dto.room.RoomUpdateDTO;
import com.ra.md05_project.model.entity.ver1.Room;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RoomService {
    Page<Room> findAll(String search, Pageable pageable);

    void delete(Long id);

    Room create(@Valid RoomAddDTO room);

    Room findById(Long id);

    Room update(Long id, @Valid RoomUpdateDTO room);

    void generateSeatsForRoom(Room room);
}
