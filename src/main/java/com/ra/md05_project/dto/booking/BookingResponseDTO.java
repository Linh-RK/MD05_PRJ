package com.ra.md05_project.dto.booking;

import com.ra.md05_project.model.constant.Payment;
import com.ra.md05_project.model.entity.ver1.BookingDetail;
import com.ra.md05_project.model.entity.ver1.ShowTime;
import com.ra.md05_project.model.entity.ver1.Snack;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingResponseDTO {

    private Double totalPrice;

    private Double totalSeat;

//    private String status;

//    private LocalDateTime createdAt;

//    private User user;

    private ShowTime showtime;

    private List<BookingDetail> bookingDetails;

    private Payment payment;

    private List<Snack> snacks;
}
