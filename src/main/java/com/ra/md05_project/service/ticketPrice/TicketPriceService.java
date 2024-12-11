package com.ra.md05_project.service.ticketPrice;

import com.ra.md05_project.dto.ticketPrice.TicketPriceAddDTO;
import com.ra.md05_project.dto.ticketPrice.TicketPriceUpdateDTO;
import com.ra.md05_project.model.entity.ver1.TicketPrice;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TicketPriceService {
    Page<TicketPrice> findAll(String search, Pageable pageable);

    void delete(Long id);

    TicketPrice create(@Valid TicketPriceAddDTO ticketPrice);

    TicketPrice findById(Long id);

    TicketPrice update(Long id, @Valid TicketPriceUpdateDTO ticketPrice);
}
