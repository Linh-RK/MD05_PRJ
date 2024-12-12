package com.ra.md05_project.service.news;

import com.ra.md05_project.dto.news.NewsAddDTO;
import com.ra.md05_project.dto.news.NewsResponseDTO;
import com.ra.md05_project.dto.news.NewsUpdateDTO;
import com.ra.md05_project.model.entity.ver1.News;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.Optional;

public interface NewsService {
    Page<NewsResponseDTO> findAll(String search, Pageable pageable);

    void delete(Long id);

    NewsResponseDTO create(@Valid NewsAddDTO news) throws IOException;

    NewsResponseDTO findById(Long id);

    NewsResponseDTO update(Long id, @Valid NewsUpdateDTO news) throws IOException;
}
