package com.ra.md05_project.model.entity.ver1;
import com.ra.md05_project.model.constant.SeatStatus;
import com.ra.md05_project.model.constant.SeatType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") 
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false) // Đặt tên cho khóa ngoại liên kết với Room
    private Room room;

    @Column(name = "row_num", length = 10)
    private Character rowNumber;

    @Column(name = "seat_num")
    private Integer seatNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 50) 
    private SeatType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private SeatStatus status;

    @Column(name = "is_deleted")
    private Boolean isDeleted;
}




