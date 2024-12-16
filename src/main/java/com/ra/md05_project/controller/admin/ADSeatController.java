package com.ra.md05_project.controller.admin;

import com.ra.md05_project.dto.seat.SeatAddDTO;
import com.ra.md05_project.dto.seat.SeatResponseDTO;
import com.ra.md05_project.dto.seat.SeatUpdateDTO;
import com.ra.md05_project.dto.user.ResponseDTOSuccess;
import com.ra.md05_project.model.entity.ver1.Festival;
import com.ra.md05_project.model.entity.ver1.Seat;
import com.ra.md05_project.service.seat.SeatService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/admin/seat")
public class ADSeatController {
    @Autowired
    private SeatService seatService;

    @GetMapping
    public ResponseEntity<Page<SeatResponseDTO>> findAllSeat(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "3") int size,
            @RequestParam(name = "sort", defaultValue = "id") String sort,
            @RequestParam(name = "filter", required = false) Long roomId,
            @RequestParam(name = "direction", defaultValue = "asc") String direction
    ) {
        Sort sortOrder = Sort.by(sort);
        sortOrder = direction.equalsIgnoreCase("desc") ? sortOrder.descending() : sortOrder.ascending();
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        Page<SeatResponseDTO> seats = seatService.findAll(roomId, pageable );
        return new ResponseEntity<>(seats, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeatResponseDTO> getSeatById (@PathVariable Long id) {
        return new ResponseEntity<>(seatService.findById(id), HttpStatus.OK);
    }

//    @PostMapping
//    public ResponseEntity<SeatResponseDTO> createSeat(@Valid @RequestBody SeatAddDTO seatAddDTO) throws IOException {
//        return ResponseEntity.status(HttpStatus.CREATED).body(seatService.create(seatAddDTO));
//    }

//    @PutMapping("/{id}")
//    public ResponseEntity<SeatResponseDTO> updateSeat(@PathVariable Long id,@Valid @RequestBody SeatUpdateDTO seatUpdateDTO) throws IOException {
//            return new ResponseEntity<>(seatService.update(id, seatUpdateDTO), HttpStatus.OK);
//
//    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteSeat(@PathVariable Long id) {
//            seatService.delete(id);
//            return new ResponseEntity<>(new ResponseDTOSuccess<>("Delete successfully",200),HttpStatus.OK);
//
//    }
}
