package com.ra.md05_project.service.banner;

import com.ra.md05_project.dto.banner.BannerAddDTO;
import com.ra.md05_project.dto.banner.BannerUpdateDTO;
import com.ra.md05_project.dto.user.ResponseDTOSuccess;
import com.ra.md05_project.model.entity.ver1.Banner;
import com.ra.md05_project.repository.BannerRepository;
import com.ra.md05_project.service.uploadFile.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerRepository bannerRepository;
    @Autowired
    private UploadService uploadService;

    @Override
    public Page<Banner> findAll(String search, Pageable pageable) {
        return bannerRepository.findAllByBannerNameContainingIgnoreCase(search, pageable);
    }

    @Override
    public Banner create(BannerAddDTO bannerAddDTO) throws IOException {
        Banner banner =  Banner.builder()
                .bannerName(bannerAddDTO.getBannerName())
                .url(uploadService.uploadFile(bannerAddDTO.getUrl()))
                .type(bannerAddDTO.getType())
                .isDeleted(false)
                .position(bannerAddDTO.getPosition())
                .build();
        try {
            return bannerRepository.save(banner);
        } catch (Exception e) {
            throw new RuntimeException("Không thể tạo mới Banner: " + e.getMessage(), e);
        }
    }

    @Override
    public Banner findById(Long id) {
        return bannerRepository.findById(id).orElseThrow(()->new NoSuchElementException("Banner Not Found"));
    }

    @Override
    public Banner update(Long id, BannerUpdateDTO bannerUpdateDTO) throws IOException {
        Banner banner = bannerRepository.findById(id).orElseThrow(()->new NoSuchElementException("Banner Not Found"));
        if(banner != null){
            Banner bannerNew = Banner.builder()
                    .id(id)
                    .bannerName(bannerUpdateDTO.getBannerName())
                    .position(bannerUpdateDTO.getPosition())
                    .type(bannerUpdateDTO.getType())
                    .isDeleted(false)
                    .url(bannerUpdateDTO.getUrl() != null && !bannerUpdateDTO.getUrl().isEmpty() ? uploadService.uploadFile(bannerUpdateDTO.getUrl()) :banner.getUrl())
                    .build();
            return bannerRepository.save(bannerNew);
        }else {
            throw new NoSuchElementException("Banner not found");
        }
    }

    @Override
    public void delete(Long id) {
        Banner banner = bannerRepository.findById(id).orElse(null);
        if(banner != null){
        bannerRepository.deleteById(id);
        }else {
            throw new NoSuchElementException("Banner not found");
        }
    }

}

