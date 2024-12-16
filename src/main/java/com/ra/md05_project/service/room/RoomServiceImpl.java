package com.ra.md05_project.service.room;

import com.ra.md05_project.dto.room.RoomAddDTO;
import com.ra.md05_project.dto.room.RoomUpdateDTO;
import com.ra.md05_project.model.constant.RoomStatus;
import com.ra.md05_project.model.constant.SeatStatus;
import com.ra.md05_project.model.constant.SeatType;
import com.ra.md05_project.model.entity.ver1.Cinema;
import com.ra.md05_project.model.entity.ver1.Room;
import com.ra.md05_project.model.entity.ver1.Seat;
import com.ra.md05_project.repository.CinemaRepository;
import com.ra.md05_project.repository.RoomRepository;
import com.ra.md05_project.repository.SeatRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

    @Autowired
    private SeatRepository seatRepository;

    @Override
    public Page<Room> findAll(String search, Pageable pageable) {
        if (search.isEmpty()) {
            return roomRepository.findAllByIsDeletedIsFalse(pageable);
        } else {
            return roomRepository.findByRoomNameContainingIgnoreCaseOrStatusContainingIgnoreCaseAndIsDeletedIsFalse(search, search, pageable);
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
                .status(RoomStatus.ACTIVE)
                .cinema(cinema)
                .rowSeat(roomAddDTO.getRowSeat())
                .colSeat(roomAddDTO.getColSeat())
                .isDeleted(false)
                .build();

        Room savedRoom = roomRepository.save(room);
        generateSeatsForRoom(savedRoom);

        return savedRoom;
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
                .isDeleted(false)
               .rowSeat(roomUpdateDTO.getRowSeat())
               .colSeat(roomUpdateDTO.getColSeat())
                .cinema(cinemaRepository.findById(roomUpdateDTO.getCinemaId())
                        .orElseThrow(() -> new NoSuchElementException("Cinema not found")))
                .build();

       if(roomUpdateDTO.getRowSeat() !=  room.getRowSeat() || roomUpdateDTO.getColSeat() !=  room.getColSeat()){
        //      Xoa het ghe cu, tao ghe moi
           seatRepository.deleteAllByRoom_Id(room.getId());
        //      tao ghe moi
           generateSeatsForRoom(roomUpdate);
       }
        return roomRepository.save(roomUpdate);
    }

    @Override
    @Transactional
    public void generateSeatsForRoom(Room room) {
        if (room.getRowSeat() == null || room.getColSeat() <1) {
            throw new IllegalArgumentException("Row and column seats must be specified for the room");
        }

        List<Seat> seats = new ArrayList<>();
        for (char letter = 'A'; letter <= room.getRowSeat(); letter++) {
            for (int col = 1; col <= room.getColSeat(); col++) {
                Seat seat = new Seat();
                seat.setRoom(room);
                seat.setRowNumber(letter);
                seat.setSeatNumber(col);
                seat.setStatus(SeatStatus.AVAILABLE);
                seat.setIsDeleted(false);

                // Set seat type based on the given rules
                if (letter == room.getRowSeat()) {
                    seat.setType(SeatType.SWEET_BOX);
                } else if (letter >= 'A'+ 2 &&
                        col > 2 && col < room.getColSeat() - 1 ) {
                    seat.setType(SeatType.VIP);
                } else {
                    seat.setType(SeatType.STANDARD);
                }
                seatRepository.save(seat);
                seats.add(seat);
            }
        }
    }

    @Override
    public Room findById(Long id) {
        return roomRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Room not found with id " + id));
    }
}

