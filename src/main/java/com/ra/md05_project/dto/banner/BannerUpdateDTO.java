package com.ra.md05_project.dto.banner;

import com.ra.md05_project.model.constant.BannerType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BannerUpdateDTO {

    @NotBlank(message = "Banner name cannot be empty")
    private String bannerName;

    private MultipartFile url;

    @NotNull(message = "Type cannot be empty")
    private BannerType type;

    @NotNull(message = "Position cannot be null")
    private String position;
}
