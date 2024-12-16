package com.ra.md05_project.model.entity.ver1;


import jakarta.persistence.*;
import lombok.*;


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

    @Column(name = "is_deleted")
    private Boolean isDeleted;

}


