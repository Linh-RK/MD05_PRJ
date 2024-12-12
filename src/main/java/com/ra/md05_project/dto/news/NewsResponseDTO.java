package com.ra.md05_project.dto.news;


import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewsResponseDTO {
    private Long id;

    private String title;

    private String content;

    private Long festivalId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<String> imagesUrl;

}
