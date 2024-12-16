package com.ra.md05_project.dto.booking;

import com.ra.md05_project.model.constant.PaymentMethod;
import com.ra.md05_project.model.entity.ver1.BookingDetail;
import com.ra.md05_project.model.entity.ver1.Seat;
import com.ra.md05_project.model.entity.ver1.ShowTime;
import com.ra.md05_project.model.entity.ver1.Snack;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingAddDTO {
    private Long showTimeId;
    private List<Long> snacksId;
    private List<Long> seatsId;
    private PaymentMethod paymentMethod;
}
