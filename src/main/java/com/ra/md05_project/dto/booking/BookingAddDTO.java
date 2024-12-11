package com.ra.md05_project.dto.booking;

import com.ra.md05_project.model.constant.Payment;
import com.ra.md05_project.model.entity.ver1.BookingDetail;
import com.ra.md05_project.model.entity.ver1.ShowTime;
import com.ra.md05_project.model.entity.ver1.Snack;
import com.ra.md05_project.model.entity.ver1.User;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingAddDTO {
    private Long userId;
    private Long showTimeId;
    private List<BookingDetail> bookingDetails;
    private List<Long> snackIds;
}
