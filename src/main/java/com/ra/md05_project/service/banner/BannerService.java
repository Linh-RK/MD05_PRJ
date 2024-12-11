package com.ra.md05_project.service.banner;

import com.ra.md05_project.dto.banner.BannerAddDTO;
import com.ra.md05_project.dto.banner.BannerUpdateDTO;
import com.ra.md05_project.model.entity.ver1.Banner;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface BannerService {
    Page<Banner> findAll(String search, Pageable pageable);

    Banner create(@Valid BannerAddDTO bannerAddDTO) throws IOException;

    Banner findById(Long id);

    Banner update(Long id, @Valid BannerUpdateDTO bannerUpdateDTO) throws IOException;

    void delete(Long id);
}
