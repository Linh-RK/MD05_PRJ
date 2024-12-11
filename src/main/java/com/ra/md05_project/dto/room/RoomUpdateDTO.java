package com.ra.md05_project.dto.room;

import com.ra.md05_project.model.constant.RoomStatus;
import com.ra.md05_project.model.entity.ver1.Cinema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomUpdateDTO {
    @NotBlank(message = "Room name is required")
    private String roomName;

    private RoomStatus status;

    private Long cinemaId;
}
