package com.ra.md05_project.service.bookingDetail;

import com.ra.md05_project.dto.bookingDetail.BookingDetailAddDTO;
import com.ra.md05_project.model.entity.ver1.Booking;
import com.ra.md05_project.model.entity.ver1.BookingDetail;
import com.ra.md05_project.model.entity.ver1.Seat;
import com.ra.md05_project.repository.TicketPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class BookingDetailServiceImpl implements BookingDetailService {
    @Autowired
    private TicketPriceRepository ticketPriceRepository;

    public Double calculatePrice(BookingDetail bookingDetail) {
        // Lấy thông tin từ booking và seat
        Booking booking = bookingDetail.getBooking();
        Seat seat = bookingDetail.getSeat();

        String movieType = booking.getShowtime().getMovie().getType().name();
        String seatType = seat.getType();
        LocalDateTime showtimeStart = booking.getShowtime().getStartTime();
        int movieDuration = booking.getShowtime().getMovie().getDuration();

        // Xác định loại ngày: WEEKDAY, WEEKEND, HOLIDAY
        DayOfWeek dayOfWeek = showtimeStart.getDayOfWeek();
        String dayType = isHoliday(showtimeStart.toLocalDate()) ? "HOLIDAY" :
                (dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) ? "WEEKEND" : "WEEKDAY";

        // Xác định khung giờ
        String timeSlot = getTimeSlot(showtimeStart.getHour());

        // Truy vấn giá từ cơ sở dữ liệu
        Double basePrice = ticketPriceRepository.findPrice(movieType, seatType, dayType, timeSlot);

        // Phụ thu nếu phim kéo dài trên 150 phút
        if (movieDuration >= 150) {
            basePrice += 10000;
        }

        // Cập nhật giá vào BookingDetail
        bookingDetail.setPrice(basePrice);
        return basePrice;
    }

    @Override
    public Page<BookingDetail> findAll(String search, Pageable pageable) {
        return null;
    }

    @Override
    public BookingDetail create(BookingDetailAddDTO bookingDetailAddDTO) throws IOException {
        return null;
    }

    @Override
    public Optional<BookingDetail> findById(Long id) {
        return Optional.empty();
    }

    private String getTimeSlot(int hour) {
        if (hour < 12) return "BEFORE_12";
        if (hour < 17) return "FROM_12_TO_17";
        if (hour < 23) return "FROM_17_TO_23";
        return "AFTER_23";
    }

    private boolean isHoliday(LocalDate date) {
        // Thêm logic kiểm tra ngày lễ (có thể lưu ngày lễ trong database hoặc file cấu hình)
        return false;
    }
}
