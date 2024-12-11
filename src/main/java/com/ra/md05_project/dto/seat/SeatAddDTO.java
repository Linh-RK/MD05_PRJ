package com.ra.md05_project.dto.seat;

import com.ra.md05_project.model.constant.SeatStatus;
import com.ra.md05_project.model.entity.ver1.Room;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeatAddDTO {
    @NotBlank(message = "Row number is required")
    private String rowNumber;

    private Integer seatNumber;

    private String type;

    private SeatStatus status;

    private Long roomId;
}
