package com.ra.md05_project.dto.seat;

import com.ra.md05_project.model.constant.SeatStatus;
import com.ra.md05_project.model.constant.SeatType;
import com.ra.md05_project.model.entity.ver1.Room;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeatUpdateDTO {

    @NotBlank(message = "Row number is required")
    private String rowNumber;

    private Integer seatNumber;

    private SeatType type;

    private SeatStatus status;

    private Long roomId;
}
