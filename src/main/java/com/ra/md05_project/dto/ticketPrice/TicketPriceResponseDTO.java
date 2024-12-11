package com.ra.md05_project.dto.ticketPrice;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketPriceResponseDTO {
    private Long id;

    private String movieType;

    private String seatType;

    private String dayType;

    private String timeSlot;

    private Double price;
}
