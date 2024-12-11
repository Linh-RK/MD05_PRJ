package com.ra.md05_project.controller.admin;


import com.ra.md05_project.dto.booking.BookingAddDTO;
import com.ra.md05_project.dto.user.ResponseDTOSuccess;
import com.ra.md05_project.model.entity.ver1.Banner;
import com.ra.md05_project.model.entity.ver1.Booking;
import com.ra.md05_project.service.booking.BookingService;
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
@RequestMapping("/admin/booking")
public class ADBookingController {
    @Autowired
    private BookingService bookingService;
    @GetMapping
    public ResponseEntity<Page<Booking>> findAllBookings(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "3") int size,
            @RequestParam(name = "search", defaultValue = "") String search,
            @RequestParam(name = "sort", defaultValue = "id") String sort,
            @RequestParam(name = "direction", defaultValue = "asc") String direction
    ) {
        Sort sortOrder = Sort.by(sort);
        sortOrder = direction.equalsIgnoreCase("desc") ? sortOrder.descending() : sortOrder.ascending();
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        Page<Booking> bookings = bookingService.findAll(search ,pageable );
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById (@PathVariable Long id) throws IOException {
        return new ResponseEntity<>(bookingService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(@Valid @RequestBody BookingAddDTO bookingAddDTO) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.create(bookingAddDTO));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable Long id) {
            bookingService.delete(id);
            return new ResponseEntity<>(new ResponseDTOSuccess<>("Delete successfully",200),HttpStatus.OK);
    }
}
