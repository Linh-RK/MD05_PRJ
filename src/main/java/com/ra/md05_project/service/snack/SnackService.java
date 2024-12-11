package com.ra.md05_project.service.snack;

import com.ra.md05_project.dto.snack.SnackAddDTO;
import com.ra.md05_project.dto.snack.SnackUpdateDTO;
import com.ra.md05_project.model.entity.ver1.Snack;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface SnackService {
    Page<Snack> findAll(String search, Pageable pageable);

    void delete(Long id);

    Snack create(@Valid SnackAddDTO snack);

    Snack findById(Long id);

    Snack update(Long id, @Valid SnackUpdateDTO snack);
}
