package com.ra.md05_project.dto.room;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomAddDTO {
    @NotBlank(message = "Room name is required")
    private String roomName;

    private Long cinemaId;
}
