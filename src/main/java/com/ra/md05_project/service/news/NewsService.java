package com.ra.md05_project.service.news;

import com.ra.md05_project.dto.news.NewsAddDTO;
import com.ra.md05_project.dto.news.NewsUpdateDTO;
import com.ra.md05_project.model.entity.ver1.News;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.Optional;

public interface NewsService {
    Page<News> findAll(String search, Pageable pageable);

    void delete(Long id);

    News create(@Valid NewsAddDTO news) throws IOException;

    News findById(Long id);

    News update(Long id, @Valid NewsUpdateDTO news) throws IOException;
}
