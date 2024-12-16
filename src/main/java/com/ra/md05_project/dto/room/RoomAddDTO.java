package com.ra.md05_project.dto.room;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import javax.validation.constraints.Min;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomAddDTO {
    @NotBlank(message = "Room name is required")
    private String roomName;

    private Long cinemaId;

    private Character rowSeat;

    @Min(value = 10, message = "Min 10")
    private Long colSeat;
}
