package com.ra.md05_project.model.entity.ver1;
import com.ra.md05_project.model.constant.DayType;
import com.ra.md05_project.model.constant.MovieType;
import com.ra.md05_project.model.constant.SeatType;
import com.ra.md05_project.model.constant.TimeSlot;
import jakarta.persistence.*;
import lombok.*;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "ticket_prices")
public class TicketPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "movie_type", nullable = false, length = 10)
    private MovieType movieType;

    @Column(name = "seat_type", nullable = false, length = 10)
    private SeatType seatType;

    @Column(name = "day_type", nullable = false, length = 10)
    private DayType dayType;

    @Column(name = "time_slot", nullable = false, length = 20)
    private TimeSlot timeSlot;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

}
