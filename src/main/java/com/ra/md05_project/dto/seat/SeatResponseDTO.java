package com.ra.md05_project.dto.seat;

import com.ra.md05_project.model.constant.SeatStatus;
import com.ra.md05_project.model.constant.SeatType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeatResponseDTO {
    private Long id;

    private Long roomId;

    private Long cinemaId;

    private String seatName;

    private SeatType type;

    private SeatStatus status;

    private Boolean isDeleted;
}
