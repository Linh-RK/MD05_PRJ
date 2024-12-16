package com.ra.md05_project.service.bookingDetail;

import com.ra.md05_project.dto.bookingDetail.BookingDetailResponseDTO;
import com.ra.md05_project.model.entity.ver1.BookingDetail;
import com.ra.md05_project.repository.BookingDetailRepository;
import com.ra.md05_project.repository.TicketPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class BookingDetailServiceImpl implements BookingDetailService {
    @Autowired
    private TicketPriceRepository ticketPriceRepository;
    @Autowired
    private BookingDetailRepository bookingDetailRepository;

    @Override
    public Page<BookingDetailResponseDTO> findAll(String search, Pageable pageable) {
        Page<BookingDetail> bookingDetails;
        if (search == null || search.isEmpty()) {
            bookingDetails = bookingDetailRepository.findAll(pageable);

        }else{
            bookingDetails =bookingDetailRepository.findByBooking_Showtime_Movie_Title(search, pageable);
        }
        // Nếu có tìm kiếm, có thể thực hiện tìm kiếm với `search`
        return bookingDetails.map(this::convertToDTO);
    }

    @Override
    public BookingDetailResponseDTO findById(Long id) {
        BookingDetail bookingDetail = bookingDetailRepository.findById(id).orElseThrow(()->new NoSuchElementException("BookingDetail not found"));
        return convertToDTO(bookingDetail);
    }

    private BookingDetailResponseDTO convertToDTO(BookingDetail bookingDetail) {
        return BookingDetailResponseDTO.builder()
                .id(bookingDetail.getId())
                .price(bookingDetail.getPrice())
                .bookingId(bookingDetail.getBooking().getId())
                .seatId(bookingDetail.getSeat().getId())
                .roomId(bookingDetail.getSeat().getRoom().getId())
                .cinemaId(bookingDetail.getSeat().getRoom().getCinema().getId())
                .build();
    }

}
