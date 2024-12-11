package com.ra.md05_project.service.showtime;

import com.ra.md05_project.dto.showtime.ShowTimeAddDTO;
import com.ra.md05_project.dto.showtime.ShowTimeUpdateDTO;
import com.ra.md05_project.model.entity.ver1.ShowTime;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ShowTimeService {
    Page<ShowTime> findAll(String search, Pageable pageable);

    void delete(Long id);

    ShowTime create(@Valid ShowTimeAddDTO showTime);

    ShowTime findById(Long id);

    ShowTime update(Long id, @Valid ShowTimeUpdateDTO showTime);
}
