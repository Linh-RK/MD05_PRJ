package com.ra.md05_project.dto.seat;

import com.ra.md05_project.model.constant.SeatStatus;
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

    private Room room;

    private String rowNumber;

    private Integer seatNumber;

    private String type;

    private SeatStatus status;
}
