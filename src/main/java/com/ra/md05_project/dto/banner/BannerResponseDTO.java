package com.ra.md05_project.dto.banner;

import com.ra.md05_project.model.constant.BannerType;
import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BannerResponseDTO {
    private String bannerName;

    private String url;

    private BannerType type; // Enum IMAGE, VIDEO

    private String position;
}
