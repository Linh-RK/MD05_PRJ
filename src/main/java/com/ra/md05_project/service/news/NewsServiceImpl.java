package com.ra.md05_project.service.news;

import com.ra.md05_project.dto.news.NewsAddDTO;
import com.ra.md05_project.dto.news.NewsUpdateDTO;
import com.ra.md05_project.model.entity.ver1.Festival;
import com.ra.md05_project.model.entity.ver1.News;
import com.ra.md05_project.repository.FestivalRepository;
import com.ra.md05_project.repository.NewsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private FestivalRepository festivalRepository; // Đảm bảo rằng bạn có FestivalRepository nếu bạn sử dụng Festival

    @Override
    public Page<News> findAll(String search, Pageable pageable) {
        if (search.isEmpty()) {
            return newsRepository.findAll(pageable); // Lấy tất cả nếu không tìm kiếm
        } else {
            // Tìm kiếm theo title hoặc content
            return newsRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(search, search, pageable);
        }
    }

    @Override
    @Transactional
    public News create(NewsAddDTO newsAddDTO)throws IOException {
        Festival festival = null;

        if (newsAddDTO.getFestivalId() != null) {
            festival = festivalRepository.findById(newsAddDTO.getFestivalId())
                    .orElseThrow(() -> new NoSuchElementException("Festival not found"));
        }

        News news = News.builder()
                .title(newsAddDTO.getTitle())
                .content(newsAddDTO.getContent())
                .isDeleted(false)
                .festival(festival)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return newsRepository.save(news);
    }

    @Override
    @Transactional
    public News update(Long id, NewsUpdateDTO newsUpdateDTO) throws IOException {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("News not found with id " + id));

        Festival festival = null;
        if (newsUpdateDTO.getFestivalId() != null) {
            festival = festivalRepository.findById(newsUpdateDTO.getFestivalId())
                    .orElseThrow(() -> new NoSuchElementException("Festival not found"));
        }

        News updatedNews = News.builder()
                .title(newsUpdateDTO.getTitle())
                .content(newsUpdateDTO.getContent())
                .updatedAt(LocalDateTime.now())
                .festival(festival)
                .build();

        return newsRepository.save(updatedNews);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("News not found with id " + id));

        news.setIsDeleted(true);
        newsRepository.save(news);
    }

    @Override
    public News findById(Long id) {
        return newsRepository.findById(id).orElseThrow(() -> new NoSuchElementException("News not found with id " + id));
    }
}

