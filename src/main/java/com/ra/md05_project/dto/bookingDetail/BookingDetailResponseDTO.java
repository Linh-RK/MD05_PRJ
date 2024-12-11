package com.ra.md05_project.dto.bookingDetail;

import com.ra.md05_project.model.entity.ver1.Booking;
import com.ra.md05_project.model.entity.ver1.Seat;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDetailResponseDTO {
    private Long id;

    private Double price;

    private Booking booking;

    private Seat seat;
}
