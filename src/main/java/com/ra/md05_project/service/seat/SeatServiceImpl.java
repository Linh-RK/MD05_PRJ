package com.ra.md05_project.service.seat;

import com.ra.md05_project.dto.seat.SeatAddDTO;
import com.ra.md05_project.dto.seat.SeatResponseDTO;
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
@Service
public class SeatServiceImpl implements SeatService {

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public Page<SeatResponseDTO> findAll(String search, Pageable pageable) {
        Page<Seat> seats;
        if (search.isEmpty()) {
            seats = seatRepository.findAllByIsDeletedIsFalse(pageable);
        } else {
            seats = seatRepository.findByRowNumberContainingIgnoreCaseOrSeatNumberContainingAndIsDeletedIsFalse(search, search, pageable);
        }

        // Chuyển đổi Page<Seat> thành Page<SeatResponseDTO>
        return seats.map(this::convertToSeatResponseDTO);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Seat not found with id " + id));
        seat.setIsDeleted(true);
        seatRepository.save(seat);
    }

    @Override
    @Transactional
    public SeatResponseDTO create(SeatAddDTO seatAddDTO) {
        Room room = roomRepository.findById(seatAddDTO.getRoomId())
                .orElseThrow(() -> new NoSuchElementException("Room not found"));

        Seat seat = Seat.builder()
                .room(room)
                .rowNumber(seatAddDTO.getRowNumber())
                .seatNumber(seatAddDTO.getSeatNumber())
                .type(seatAddDTO.getType())
                .isDeleted(false)
                .status(seatAddDTO.getStatus())
                .build();

        seat = seatRepository.save(seat);

        return convertToSeatResponseDTO(seat);
    }

    @Override
    @Transactional
    public SeatResponseDTO update(Long id, SeatUpdateDTO seatUpdateDTO) {
        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Seat not found with id " + id));

        seat.setRowNumber(seatUpdateDTO.getRowNumber());
        seat.setSeatNumber(seatUpdateDTO.getSeatNumber());
        seat.setType(seatUpdateDTO.getType());
        seat.setStatus(seatUpdateDTO.getStatus());
        seat.setRoom(roomRepository.findById(seatUpdateDTO.getRoomId())
                .orElseThrow(() -> new NoSuchElementException("Room not found")));

        seat = seatRepository.save(seat);

        return convertToSeatResponseDTO(seat);
    }

    @Override
    public SeatResponseDTO findById(Long id) {
        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Seat not found with id " + id));

        return convertToSeatResponseDTO(seat);
    }

    // Hàm chuyển đổi từ Seat sang SeatResponseDTO
    private SeatResponseDTO convertToSeatResponseDTO(Seat seat) {
        return SeatResponseDTO.builder()
                .id(seat.getId())
                .roomId(seat.getRoom().getId())
                .cinemaId(seat.getRoom().getCinema().getId())  // Giả sử Room có mối quan hệ với Cinema
                .seatName(seat.getRowNumber() + seat.getSeatNumber())  // CONCAT(rowNumber, seatNumber)
                .type(seat.getType())
                .status(seat.getStatus())
                .isDeleted(seat.getIsDeleted())
                .build();
    }
}

