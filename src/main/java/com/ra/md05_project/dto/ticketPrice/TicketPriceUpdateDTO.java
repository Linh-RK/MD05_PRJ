package com.ra.md05_project.dto.ticketPrice;

import com.ra.md05_project.model.constant.DayType;
import com.ra.md05_project.model.constant.MovieType;
import com.ra.md05_project.model.constant.SeatType;
import com.ra.md05_project.model.constant.TimeSlot;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketPriceUpdateDTO {
    @NotNull(message = "Movie type is required")
    private MovieType movieType;

    @NotNull(message = "Seat type is required")
    private SeatType seatType;

    @NotNull(message = "Day type is required")
    private DayType dayType;

    @NotNull(message = "Time slot is required")
    private TimeSlot timeSlot;

    @NotNull(message = "Price is required")
    private Double price;
}
