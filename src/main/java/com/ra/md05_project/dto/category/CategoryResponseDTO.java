package com.ra.md05_project.dto.category;

import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResponseDTO {
    private Long id;

    private String categoryName;

    private Boolean isDeleted;

    private List<Long> movies;
}
