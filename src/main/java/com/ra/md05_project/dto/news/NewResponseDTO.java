package com.ra.md05_project.dto.news;

import com.ra.md05_project.model.entity.ver1.Festival;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewResponseDTO {
    private Long id;

    private String title;

    private String content;

    private Festival festival;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
