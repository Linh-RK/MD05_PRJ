package com.ra.md05_project.model.entity.ver1;

import com.ra.md05_project.model.constant.PaymentMethod;
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

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "total_seat")
    private Integer totalSeat;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "showtime_id")
    private ShowTime showtime;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private List<BookingDetail> bookingDetails;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment")
    private PaymentMethod paymentMethod;

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



