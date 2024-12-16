package com.ra.md05_project.model.entity.ver1;
import com.ra.md05_project.model.constant.RoomStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") 
    private Long id;

    @Column(name = "room_name", nullable = false) 
    private String roomName;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private RoomStatus status;

    @ManyToOne
    @JoinColumn(name = "cinema_id", nullable = false) // Đặt tên cho khóa ngoại liên kết với Cinema
    private Cinema cinema;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "row_seat")
    private Character rowSeat;

    @Column(name = "col_seat")
    private long colSeat;
}

