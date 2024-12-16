package com.ra.md05_project.dto.booking;

import com.ra.md05_project.model.constant.PaymentMethod;
import com.ra.md05_project.model.entity.ver1.ShowTime;
import com.ra.md05_project.model.entity.ver1.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingResponseDTO {
    private Long id;
    private Double totalPrice;
    private Double totalSeat;
    private LocalDateTime createdAt;
    private Long userId;
    private Long showtimeId;
    private Long movieId;
    private PaymentMethod paymentMethod;
    private List<Long> seatsId;
    private List<Long> snacksId;
}
