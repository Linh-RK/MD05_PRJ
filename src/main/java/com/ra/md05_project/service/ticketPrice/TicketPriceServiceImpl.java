package com.ra.md05_project.service.ticketPrice;

import com.ra.md05_project.dto.ticketPrice.TicketPriceAddDTO;
import com.ra.md05_project.dto.ticketPrice.TicketPriceUpdateDTO;
import com.ra.md05_project.model.entity.ver1.TicketPrice;
import com.ra.md05_project.repository.TicketPriceRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TicketPriceServiceImpl implements TicketPriceService {

    @Autowired
    private TicketPriceRepository ticketPriceRepository;

    @Override
    public Page<TicketPrice> findAll(String search, Pageable pageable) {
        // Tìm kiếm theo loại phim, loại ghế
        if (search == null || search.isEmpty()) {
            return ticketPriceRepository.findAll(pageable);
        } else {
            return ticketPriceRepository.findByMovieTypeContainingIgnoreCaseOrSeatTypeContainingIgnoreCase(search, search, pageable);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        TicketPrice ticketPrice = ticketPriceRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("TicketPrice not found with id " + id));

        // Đánh dấu là đã xóa thay vì xóa thật sự trong cơ sở dữ liệu
        ticketPrice.setIsDeleted(true);
        ticketPriceRepository.save(ticketPrice);
    }

    @Override
    @Transactional
    public TicketPrice create(@Valid TicketPriceAddDTO ticketPriceAddDTO) {
        TicketPrice ticketPrice = new TicketPrice();
        ticketPrice.setMovieType(ticketPriceAddDTO.getMovieType());
        ticketPrice.setSeatType(ticketPriceAddDTO.getSeatType());
        ticketPrice.setDayType(ticketPriceAddDTO.getDayType());
        ticketPrice.setTimeSlot(ticketPriceAddDTO.getTimeSlot());
        ticketPrice.setPrice(ticketPriceAddDTO.getPrice());
        ticketPrice.setIsDeleted(false);

        return ticketPriceRepository.save(ticketPrice);
    }

    @Override
    public TicketPrice findById(Long id) {
        return ticketPriceRepository.findById(id).orElseThrow(() -> new NoSuchElementException("TicketPrice not found with id " + id));
    }

    @Override
    @Transactional
    public TicketPrice update(Long id, @Valid TicketPriceUpdateDTO ticketPriceUpdateDTO) {
        TicketPrice ticketPrice = ticketPriceRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("TicketPrice not found with id " + id));

        // Cập nhật thông tin TicketPrice
        ticketPrice.setMovieType(ticketPriceUpdateDTO.getMovieType());
        ticketPrice.setSeatType(ticketPriceUpdateDTO.getSeatType());
        ticketPrice.setDayType(ticketPriceUpdateDTO.getDayType());
        ticketPrice.setTimeSlot(ticketPriceUpdateDTO.getTimeSlot());
        ticketPrice.setPrice(ticketPriceUpdateDTO.getPrice());

        return ticketPriceRepository.save(ticketPrice);
    }
}