package com.ra.md05_project.dto.ticketPrice;

import com.ra.md05_project.model.constant.DayType;
import com.ra.md05_project.model.constant.MovieType;
import com.ra.md05_project.model.constant.SeatType;
import com.ra.md05_project.model.constant.TimeSlot;
import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketPriceResponseDTO {
    private Long id;

    private MovieType movieType;

    private SeatType seatType;

    private DayType dayType;

    private TimeSlot timeSlot;

    private Double price;
}
