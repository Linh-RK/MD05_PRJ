package com.ra.md05_project.dto.bookingDetail;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDetailResponseDTO {
    private Long id;
    private Double price;
    private Long bookingId;
    private Long seatId;
    private Long roomId;
    private Long cinemaId;
}
