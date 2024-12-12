package com.ra.md05_project.dto.booking;

import com.ra.md05_project.model.entity.ver1.BookingDetail;
import lombok.*;

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
