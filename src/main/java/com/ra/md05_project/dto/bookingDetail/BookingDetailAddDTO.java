package com.ra.md05_project.dto.bookingDetail;

import com.ra.md05_project.model.entity.ver1.Booking;
import com.ra.md05_project.model.entity.ver1.Seat;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDetailAddDTO {
    private Long bookingId;
    private Long seatId;
}
