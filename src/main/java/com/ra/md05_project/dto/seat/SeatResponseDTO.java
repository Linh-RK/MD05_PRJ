package com.ra.md05_project.dto.seat;

import com.ra.md05_project.model.constant.SeatStatus;
import com.ra.md05_project.model.constant.SeatType;
import com.ra.md05_project.model.entity.ver1.Room;
import jakarta.persistence.*;
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

    private String seatName; // = CONCAT(rowNumber,seatNumber)

    private SeatType type;

    private SeatStatus status;

    private Boolean isDeleted;
}
