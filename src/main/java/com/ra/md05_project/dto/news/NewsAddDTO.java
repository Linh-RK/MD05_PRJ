package com.ra.md05_project.dto.news;

import com.ra.md05_project.model.entity.ver1.Festival;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewsAddDTO {
    private String title;
    private String content;
    private Long festivalId;
}
