package com.ra.md05_project.controller.admin;

import com.ra.md05_project.dto.banner.BannerAddDTO;
import com.ra.md05_project.dto.banner.BannerResponseDTO;
import com.ra.md05_project.dto.banner.BannerUpdateDTO;
import com.ra.md05_project.dto.user.ResponseDTOSuccess;
import com.ra.md05_project.model.constant.BannerType;
import com.ra.md05_project.model.entity.ver1.Banner;
import com.ra.md05_project.model.entity.ver1.User;
import com.ra.md05_project.service.banner.BannerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/admin/banner")
public class ADBannerController {

    @Autowired
    private BannerService bannerService;

    @GetMapping
    public ResponseEntity<Page<Banner>> findAllBanners(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "3") int size,
            @RequestParam(name = "search", defaultValue = "") String search,
            @RequestParam(name = "type", defaultValue = "") BannerType type,
            @RequestParam(name = "position", defaultValue = "") String position,
            @RequestParam(name = "sort", defaultValue = "id") String sort,
            @RequestParam(name = "direction", defaultValue = "asc") String direction
    ) {
        Sort sortOrder = Sort.by(sort);
        sortOrder = direction.equalsIgnoreCase("desc") ? sortOrder.descending() : sortOrder.ascending();
        Pageable pageable = PageRequest.of(page, size, sortOrder);

        Page<Banner> banners = bannerService.findAll(search, type, position, pageable);
        return new ResponseEntity<>(banners, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Banner> getBannerById (@PathVariable Long id) throws IOException {
        return new ResponseEntity<>(bannerService.findById(id), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<Banner> createBanner(@Valid @ModelAttribute BannerAddDTO bannerAddDTO) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(bannerService.create(bannerAddDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Banner> updateBanner (@PathVariable Long id,@Valid @ModelAttribute BannerUpdateDTO bannerUpdateDTO) throws IOException {
            Banner updatedBanner = bannerService.update(id, bannerUpdateDTO);
            return new ResponseEntity<>(updatedBanner, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBanner(@PathVariable Long id) {
            bannerService.delete(id);
            return new ResponseEntity<>(new ResponseDTOSuccess<>("Delete successfully",200),HttpStatus.OK);
    }
}
