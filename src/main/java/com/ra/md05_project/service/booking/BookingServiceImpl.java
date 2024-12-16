package com.ra.md05_project.service.booking;

import com.ra.md05_project.dto.booking.BookingAddDTO;
import com.ra.md05_project.dto.booking.BookingResponseDTO;
import com.ra.md05_project.model.constant.*;
import com.ra.md05_project.model.entity.ver1.*;
import com.ra.md05_project.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingDetailRepository bookingDetailRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private SnackRepository snackRepository;
    @Autowired
    private ShowTimeRepository showTimeRepository;
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private TicketPriceRepository ticketPriceRepository;
    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Page<BookingResponseDTO> findAll(String search, Pageable pageable) {
        Page<Booking> bookings;
        if (search == null || search.isEmpty()) {
            bookings = bookingRepository.findAll(pageable);
        } else {
            bookings = bookingRepository.findByShowtime_Movie_Title(search, pageable);
        }
        return bookings.map(this::convertToResponseDTO);
    }

    @Override
    public BookingResponseDTO create(BookingAddDTO bookingAddDTO, User user) throws IOException {
        // Chuyển đổi DTO sang entity Booking
        Booking booking = new Booking();
        ShowTime showTime = showTimeRepository.findById(bookingAddDTO.getShowTimeId())
                .orElseThrow(() -> new RuntimeException("ShowTime not found"));
        // Set thông tin cho booking
        booking.setUser(user);
        booking.setTotalSeat(bookingAddDTO.getSeatsId().size());
        booking.setShowtime(showTime);
        booking.setCreatedAt(LocalDateTime.now());
        booking.setPaymentMethod(bookingAddDTO.getPaymentMethod());
        booking.setIsDeleted(false);
        Booking newBooking = bookingRepository.save(booking);

        // Snack
        List<Snack> snackList = new ArrayList<>();
        for(Long snackId : bookingAddDTO.getSnacksId() ){
            snackRepository.findById(snackId).ifPresent(snackList::add);
        }
        newBooking.setSnacks(snackList);
        Double snackPrice =
                snackList.isEmpty() ? 0.0
                :snackList.stream().mapToDouble(Snack::getPrice).sum();

        // tao BookingDetail
        List<BookingDetail> bookingDetailList = new ArrayList<>();
        Double seatPrice = 0.0;
        for (Long seatId : bookingAddDTO.getSeatsId() ){
            Seat seat = seatRepository.findById(seatId).orElse(null);
            if(seat != null){
                BookingDetail bookingDetail = BookingDetail.builder()
                        .seat(seat)
                        .price(calculateSeatPrice(seat,showTime))
                        .booking(booking)
                        .isDeleted(false)
                        .build();
                bookingDetailList.add(bookingDetail);
                bookingDetailRepository.save(bookingDetail);
                seatPrice += calculateSeatPrice(seat,showTime);
            }
        }
        newBooking.setBookingDetails(bookingDetailList);

        // Tính toán tổng giá
        newBooking.setTotalPrice(snackPrice + seatPrice);

        // Tao Payment
        Payment payment = Payment.builder()
                .amount(newBooking.getTotalPrice())
                .paymentMethod(newBooking.getPaymentMethod())
                .paymentStatus(PaymentStatus.PENDING)
                .paymentTime(LocalDateTime.now())
                .transactionId(null) // ma giao dich ben thu 3
                .booking(newBooking)
                .build();
        paymentRepository.save(payment);

        return convertToResponseDTO(bookingRepository.save(newBooking));
    }

    @Override
    public BookingResponseDTO findById(Long id) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Booking not found"));
        return convertToResponseDTO(booking);
    }

    @Override
    public void delete(Long id) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Booking not found"));
        booking.setIsDeleted(true);
        bookingRepository.save(booking);
    }

    private Double calculateSeatPrice(Seat seat, ShowTime showTime) {
        MovieType movieType = showTime.getMovie().getType();
        SeatType seatType = seat.getType();
        LocalDateTime showtimeStart = showTime.getStartTime();
        int movieDuration = showTime.getMovie().getDuration();

        // Xác định loại ngày: WEEKDAY, WEEKEND, HOLIDAY
        DayOfWeek dayOfWeek = showtimeStart.getDayOfWeek();
        DayType dayType = isHoliday(showtimeStart.toLocalDate()) ||
                (dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY)
                ? DayType.WEEKEND_HOLIDAY
                : DayType.WEEKDAY;

        // Xác định khung giờ
        TimeSlot timeSlot = getTimeSlot(showtimeStart.getHour());

        // Truy vấn giá từ cơ sở dữ liệu
        Double basePrice = ticketPriceRepository.findPrice(movieType, seatType, dayType, timeSlot);

        // Phụ thu nếu phim kéo dài trên 150 phút
        if (movieDuration >= 150) {
            basePrice += 10000;
        }

        return basePrice;
    }

    private TimeSlot getTimeSlot(int hour) {
        if (hour < 12) return TimeSlot.MORNING;
        if (hour < 17) return TimeSlot.AFTERNOON;
        if (hour < 23) return TimeSlot.EVENING;
        return TimeSlot.MIDDLE_NIGHT;
    }

    private boolean isHoliday(LocalDate date) {
        // Thêm logic kiểm tra ngày lễ (có thể lưu ngày lễ trong database hoặc file cấu hình)
        return false;
    }

    private BookingResponseDTO convertToResponseDTO(Booking booking) {
        return BookingResponseDTO.builder()
                .id(booking.getId())
                .totalPrice(booking.getTotalPrice())
                .totalSeat((double) booking.getTotalSeat())
                .createdAt(booking.getCreatedAt())
                .userId(booking.getUser().getId())
                .showtimeId(booking.getShowtime().getId())
                .movieId(booking.getShowtime().getMovie().getId())
                .paymentMethod(booking.getPaymentMethod())
                .seatsId(booking.getBookingDetails().stream()
                        .map(detail -> detail.getSeat().getId())
                        .collect(Collectors.toList()))
                .snacksId(booking.getSnacks().stream()
                        .map(Snack::getId)
                        .collect(Collectors.toList()))
                .build();
    }
}
