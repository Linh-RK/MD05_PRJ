package com.ra.md05_project.service.room;

import com.ra.md05_project.dto.room.RoomAddDTO;
import com.ra.md05_project.dto.room.RoomUpdateDTO;
import com.ra.md05_project.model.entity.ver1.Cinema;
import com.ra.md05_project.model.entity.ver1.Room;
import com.ra.md05_project.repository.CinemaRepository;
import com.ra.md05_project.repository.RoomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private CinemaRepository cinemaRepository;

    @Override
    public Page<Room> findAll(String search, Pageable pageable) {
        if (search.isEmpty()) {
            return roomRepository.findAll(pageable);
        } else {
            return roomRepository.findByRoomNameContainingIgnoreCaseOrStatusContainingIgnoreCase(search, search, pageable);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Room not found with id " + id));

        room.setIsDeleted(true);

        roomRepository.save(room);
    }

    @Override
    @Transactional
    public Room create(RoomAddDTO roomAddDTO) {
        Cinema cinema = cinemaRepository.findById(roomAddDTO.getCinemaId())
                .orElseThrow(() -> new NoSuchElementException("Cinema not found"));

        Room room = Room.builder()
                .roomName(roomAddDTO.getRoomName())
                .status(roomAddDTO.getStatus())
                .cinema(cinema)
                .isDeleted(false)
                .build();

        return roomRepository.save(room);
    }

    @Override
    @Transactional
    public Room update(Long id, RoomUpdateDTO roomUpdateDTO) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Room not found with id " + id));

       Room roomUpdate = Room.builder()
               .id(id)
                .roomName(roomUpdateDTO.getRoomName())
                .status(roomUpdateDTO.getStatus())
                .cinema(cinemaRepository.findById(roomUpdateDTO.getCinemaId())
                        .orElseThrow(() -> new NoSuchElementException("Cinema not found")))
                .build();

        return roomRepository.save(roomUpdate);
    }

    @Override
    public Room findById(Long id) {
        return roomRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Room not found with id " + id));
    }
}

