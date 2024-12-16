package com.ra.md05_project.controller.user;

import com.ra.md05_project.dto.booking.BookingAddDTO;
import com.ra.md05_project.dto.booking.BookingResponseDTO;
import com.ra.md05_project.dto.user.ResponseDTOSuccess;
import com.ra.md05_project.model.constant.PaymentMethod;
import com.ra.md05_project.model.entity.ver1.Booking;
import com.ra.md05_project.model.entity.ver1.User;
import com.ra.md05_project.security.UserPrinciple;
import com.ra.md05_project.service.booking.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/user/booking")
public class UBookingController {
    @Autowired
    private BookingService bookingService;
    @GetMapping
    public ResponseEntity<Page<BookingResponseDTO>> findAllBookings(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "3") int size,
            @RequestParam(name = "search", defaultValue = "") String search,
            @RequestParam(name = "sort", defaultValue = "id") String sort,
            @RequestParam(name = "direction", defaultValue = "asc") String direction,
            @AuthenticationPrincipal UserPrinciple userPrincipal
    ) {
        User currentUser = userPrincipal.getUser();
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            Sort sortOrder = Sort.by(sort);
            sortOrder = direction.equalsIgnoreCase("desc") ? sortOrder.descending() : sortOrder.ascending();
            Pageable pageable = PageRequest.of(page, size, sortOrder);
            Page<BookingResponseDTO> bookings = bookingService.findAll(search ,pageable );
            return new ResponseEntity<>(bookings, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponseDTO> getBookingById (
            @PathVariable Long id,
            @AuthenticationPrincipal UserPrinciple userPrincipal) throws IOException {
        User currentUser = userPrincipal.getUser();
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            return new ResponseEntity<>(bookingService.findById(id), HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<BookingResponseDTO> createBooking(
            @Valid @RequestBody BookingAddDTO bookingAddDTO,
            @AuthenticationPrincipal UserPrinciple userPrincipal) throws IOException {
        User currentUser = userPrincipal.getUser();
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            return new ResponseEntity<>(bookingService.create(bookingAddDTO,currentUser),HttpStatus.CREATED);
        }
     }

//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteBooking(@PathVariable Long id) {
//        bookingService.delete(id);
//        return new ResponseEntity<>(new ResponseDTOSuccess<>("Delete successfully",200),HttpStatus.OK);
//    }
}
