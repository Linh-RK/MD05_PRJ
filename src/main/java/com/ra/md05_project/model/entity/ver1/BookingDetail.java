package com.ra.md05_project.model.entity.ver1;

import com.ra.md05_project.repository.TicketPriceRepository;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "price", nullable = false)
    private Double price;

    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false) // Liên kết với bảng Booking
    private Booking booking;

    @ManyToOne
    @JoinColumn(name = "seat_id", nullable = false) // Liên kết với bảng Seat
    private Seat seat;

}

