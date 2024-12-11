package com.ra.md05_project.controller.admin;

import com.ra.md05_project.dto.ticketPrice.TicketPriceAddDTO;
import com.ra.md05_project.dto.ticketPrice.TicketPriceUpdateDTO;
import com.ra.md05_project.dto.user.ResponseDTOSuccess;
import com.ra.md05_project.model.entity.ver1.TicketPrice;
import com.ra.md05_project.service.ticketPrice.TicketPriceService;
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
@RequestMapping("/admin/ticket-price")
public class ADTicketPriceController {
    @Autowired
    private TicketPriceService ticketPriceService;

    @GetMapping
    public ResponseEntity<Page<TicketPrice>> findAllTicketPrice(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "3") int size,
            @RequestParam(name = "search", defaultValue = "") String search,
            @RequestParam(name = "sort", defaultValue = "id") String sort,
            @RequestParam(name = "direction", defaultValue = "asc") String direction
    ) {
        Sort sortOrder = Sort.by(sort);
        sortOrder = direction.equalsIgnoreCase("desc") ? sortOrder.descending() : sortOrder.ascending();
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        Page<TicketPrice> ticketPrices = ticketPriceService.findAll(search ,pageable );
        return new ResponseEntity<>(ticketPrices, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketPrice> getBookingById (@PathVariable Long id) {
        return new ResponseEntity<>(ticketPriceService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TicketPrice> createTicketPrice(@Valid @RequestBody TicketPriceAddDTO ticketPriceAddDTO) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketPriceService.create(ticketPriceAddDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketPrice> updateTicketPrice(@PathVariable Long id,@Valid @RequestBody TicketPriceUpdateDTO ticketPriceUpdateDTO) throws IOException {
            return new ResponseEntity<>(ticketPriceService.update(id, ticketPriceUpdateDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTicketPrice(@PathVariable Long id) {
            ticketPriceService.delete(id);
            return new ResponseEntity<>(new ResponseDTOSuccess<>("Delete successfully",200),HttpStatus.OK);
    }
}
