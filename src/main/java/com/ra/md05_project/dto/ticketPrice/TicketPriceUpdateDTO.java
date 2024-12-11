package com.ra.md05_project.dto.ticketPrice;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketPriceUpdateDTO {
    @NotNull(message = "Movie type is required")
    private String movieType;

    @NotNull(message = "Seat type is required")
    private String seatType;

    @NotNull(message = "Day type is required")
    private String dayType;

    @NotNull(message = "Time slot is required")
    private String timeSlot;

    @NotNull(message = "Price is required")
    private Double price;
}
