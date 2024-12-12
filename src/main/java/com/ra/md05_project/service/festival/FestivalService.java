package com.ra.md05_project.service.festival;

import com.ra.md05_project.dto.festival.FestivalAddDTO;
import com.ra.md05_project.dto.festival.FestivalUpdateDTO;
import com.ra.md05_project.model.entity.ver1.Festival;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface FestivalService {
    Page<Festival> findAll(String search, Pageable pageable);

    void delete(Long id);

    Festival create(@Valid FestivalAddDTO festival) throws IOException;

    Festival findById(Long id);

    Festival update(Long id, @Valid FestivalUpdateDTO festival) throws IOException;
}
