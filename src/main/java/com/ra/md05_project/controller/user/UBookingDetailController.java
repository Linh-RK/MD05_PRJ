package com.ra.md05_project.controller.user;

import com.ra.md05_project.dto.bookingDetail.BookingDetailResponseDTO;
import com.ra.md05_project.model.entity.ver1.User;
import com.ra.md05_project.security.UserPrinciple;
import com.ra.md05_project.service.bookingDetail.BookingDetailService;
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

@RestController
@RequestMapping("/user/booking-detail")
public class UBookingDetailController {
    @Autowired
    private BookingDetailService bookingDetailService;

    @GetMapping
    public ResponseEntity<Page<BookingDetailResponseDTO>> findAllBookingDetails(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "3") int size,
            @RequestParam(name = "search", defaultValue = "") String search,
            @RequestParam(name = "sort", defaultValue = "id") String sort,
            @RequestParam(name = "direction", defaultValue = "asc") String direction,
            @AuthenticationPrincipal UserPrinciple userPrincipal
    ) {
        User currentUser = userPrincipal.getUser();
        Sort sortOrder = Sort.by(sort);
        sortOrder = direction.equalsIgnoreCase("desc") ? sortOrder.descending() : sortOrder.ascending();
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        Page<BookingDetailResponseDTO> BookingDetails = bookingDetailService.findAll(search ,pageable );
        return new ResponseEntity<>(BookingDetails, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDetailResponseDTO> getBookingDetailById (
            @PathVariable Long id,
            @AuthenticationPrincipal UserPrinciple userPrincipal) throws IOException {
        User currentUser = userPrincipal.getUser();
        return new ResponseEntity<>(bookingDetailService.findById(id), HttpStatus.OK);
    }
}
