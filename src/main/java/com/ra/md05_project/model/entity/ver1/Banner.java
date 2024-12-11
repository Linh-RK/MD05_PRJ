package com.ra.md05_project.model.entity.ver1;
import com.ra.md05_project.model.constant.BannerType;
import jakarta.persistence.*;
import lombok.*;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "banners")
public class Banner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "banner_name", nullable = false, length = 255)
    private String bannerName;

    @Column(name = "url", nullable = false, length = 255)
    private String url;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private BannerType type; // Enum IMAGE, VIDEO

    @Column(name = "position", nullable = false, length = 255)
    private String position;

    @Column(name = "is_deleted")
    private Boolean isDeleted;
}

