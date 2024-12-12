package com.ra.md05_project.service.booking;

import com.ra.md05_project.dto.booking.BookingAddDTO;
import com.ra.md05_project.model.entity.ver1.*;
import com.ra.md05_project.repository.BookingRepository;
import com.ra.md05_project.repository.ShowTimeRepository;
import com.ra.md05_project.repository.SnackRepository;
import com.ra.md05_project.repository.UserRepository;
import com.ra.md05_project.service.bookingDetail.BookingDetailService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingDetailService bookingDetailService;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private SnackRepository snackRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShowTimeRepository showTimeRepository;

    @Transactional
    public Double calculateTotalPrice(Booking booking) {
        // Tính tổng giá vé cho Booking
        Double totalPrice = 0.0;

        // Tính giá cho từng BookingDetail (từng vé)
        for (BookingDetail detail : booking.getBookingDetails()) {
            totalPrice += bookingDetailService.calculatePrice(detail); // Tính giá cho mỗi vé
        }

        // Tính giá cho các món ăn nhẹ
        totalPrice += calculateSnackPrice(booking.getSnacks());

        // Cập nhật tổng giá cho Booking
        booking.setTotalPrice(totalPrice);

        return totalPrice;
    }

    private Double calculateSnackPrice(List<Snack> snacks) {
        if (snacks == null || snacks.isEmpty()) return 0.0;
        return snacks.stream().mapToDouble(Snack::getPrice).sum();
    }

    @Transactional
    public void processBooking(Booking booking) {
        // Tính toán giá cho Booking và lưu thông tin
        Double totalPrice = calculateTotalPrice(booking);
        // Lưu cập nhật thông tin booking vào cơ sở dữ liệu
        bookingRepository.save(booking);
    }

    @Override
    public Page<Booking> findAll(String search, Pageable pageable) {
        if (search == null || search.isEmpty()) {
            return bookingRepository.findAll(pageable);
        }
        // Nếu có tìm kiếm, có thể thực hiện tìm kiếm với `search`
        return bookingRepository.findByShowtime_Movie_Title(search, pageable);
    }

    @Override
    public Booking create(BookingAddDTO bookingAddDTO) throws IOException {
        // Chuyển đổi DTO sang entity Booking
        Booking booking = new Booking();
        if (bookingAddDTO.getBookingDetails() == null || bookingAddDTO.getBookingDetails().isEmpty()) {
            throw new IllegalArgumentException("Booking details cannot be empty");
        }
        User user = userRepository.findById(bookingAddDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        ShowTime showTime = showTimeRepository.findById(bookingAddDTO.getShowTimeId())
                .orElseThrow(() -> new RuntimeException("ShowTime not found"));

        // Set thông tin cho booking
        booking.setUser(user);
        booking.setShowtime(showTime);
        booking.setCreatedAt(LocalDateTime.now());
        booking.setBookingDetails(bookingAddDTO.getBookingDetails());
        booking.setSnacks(snackRepository.findAllById(bookingAddDTO.getSnackIds()));

        // Tính toán tổng giá
        processBooking(booking);

        return bookingRepository.save(booking);
    }

    @Override
    public Booking findById(Long id) {
        return bookingRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Booking not found"));
    }

    @Override
    public void delete(Long id) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Booking not found"));
        booking.setIsDeleted(true);
        bookingRepository.save(booking);
    }
}
