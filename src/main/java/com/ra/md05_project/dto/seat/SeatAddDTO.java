package com.ra.md05_project.dto.seat;

import com.ra.md05_project.model.constant.SeatStatus;
import com.ra.md05_project.model.constant.SeatType;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeatAddDTO {
    @NotBlank(message = "Row number is required")
    private Character rowNumber;

    private Integer seatNumber;

    private SeatType type;

    private SeatStatus status;

    private Long roomId;
}
