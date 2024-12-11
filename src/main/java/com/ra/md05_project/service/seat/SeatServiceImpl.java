package com.ra.md05_project.service.seat;

import com.ra.md05_project.dto.seat.SeatAddDTO;
import com.ra.md05_project.dto.seat.SeatUpdateDTO;
import com.ra.md05_project.model.entity.ver1.Room;
import com.ra.md05_project.model.entity.ver1.Seat;
import com.ra.md05_project.repository.RoomRepository;
import com.ra.md05_project.repository.SeatRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
@Service
public class SeatServiceImpl implements SeatService {

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public Page<Seat> findAll(String search, Pageable pageable) {
        if (search.isEmpty()) {
            return seatRepository.findAll(pageable);
        } else {
            // Tìm kiếm theo tên phòng hoặc trạng thái ghế
            return seatRepository.findByRowNumberContainingIgnoreCaseOrSeatNumberContaining(search, search, pageable);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Seat not found with id " + id));
        seatRepository.delete(seat);
    }

    @Override
    @Transactional
    public Seat create(SeatAddDTO seatAddDTO) {
        Room room = roomRepository.findById(seatAddDTO.getRoomId())
                .orElseThrow(() -> new NoSuchElementException("Room not found"));

        Seat seat = Seat.builder()
                .room(room)
                .rowNumber(seatAddDTO.getRowNumber())
                .seatNumber(seatAddDTO.getSeatNumber())
                .type(seatAddDTO.getType())
                .status(seatAddDTO.getStatus())
                .build();

        return seatRepository.save(seat);
    }

    @Override
    @Transactional
    public Seat update(Long id, SeatUpdateDTO seatUpdateDTO) {

        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Seat not found with id " + id));


        Seat seatUpdate = Seat.builder()
                .rowNumber(seatUpdateDTO.getRowNumber())
                .seatNumber(seatUpdateDTO.getSeatNumber())
                .type(seatUpdateDTO.getType())
                .status(seatUpdateDTO.getStatus())
                .room(roomRepository.findById(seatUpdateDTO.getRoomId())
                        .orElseThrow(() -> new NoSuchElementException("Room not found")))
                .build();

        return seatRepository.save(seatUpdate);
    }

    @Override
    public Seat findById(Long id) {
        return seatRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Seat not found with id " + id));
    }
}
