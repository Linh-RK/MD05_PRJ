package com.ra.md05_project.dto.room;

import com.ra.md05_project.model.constant.RoomStatus;
import com.ra.md05_project.model.entity.ver1.Cinema;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomResponseDTO {
    private Long id;

    private String roomName;

    private RoomStatus status;

    private Cinema cinema;
}
