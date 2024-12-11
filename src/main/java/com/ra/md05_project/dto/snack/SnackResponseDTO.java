package com.ra.md05_project.dto.snack;

import com.ra.md05_project.model.entity.ver1.Snack;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SnackResponseDTO {
    private Long id;

    private String name;

    private Double price;

    private String type;

    private List<Snack> snacks;
}
