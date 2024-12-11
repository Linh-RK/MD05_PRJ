package com.ra.md05_project.model.entity.ver1;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Snack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "type", nullable = false, length = 50)
    private String type;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @ManyToMany
    @JoinTable(
            name = "booking_snack",
            joinColumns = @JoinColumn(name = "snack_id"),
            inverseJoinColumns = @JoinColumn(name = "booking_id")
    )
    private List<Snack> snacks;
}

