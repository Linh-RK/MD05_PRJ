package com.ra.md05_project.dto.snack;

import com.ra.md05_project.model.entity.ver1.Snack;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SnackAddDTO {
    @NotNull(message = "Name is required")
    private String name;

    @NotNull(message = "Type is required")
    private String type;

    @NotNull(message = "Price is required")
    private Double price;


}
