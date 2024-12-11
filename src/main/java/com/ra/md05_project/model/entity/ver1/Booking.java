package com.ra.md05_project.model.entity.ver1;

import com.ra.md05_project.model.constant.Payment;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    @Column(name = "total_seat", nullable = false)
    private Double totalSeat;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "showtime_id", nullable = false)
    private ShowTime showtime;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private List<BookingDetail> bookingDetails;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment", nullable = false)
    private Payment payment;

    @ManyToMany
    @JoinTable(
            name = "booking_snack", // Tên bảng trung gian
            joinColumns = @JoinColumn(name = "booking_id"), // Cột từ Booking
            inverseJoinColumns = @JoinColumn(name = "snack_id") // Cột từ Snack
    )
    private List<Snack> snacks;

    @Column(name = "is_deleted")
    private Boolean isDeleted;
}

