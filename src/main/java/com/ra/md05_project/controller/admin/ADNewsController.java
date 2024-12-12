package com.ra.md05_project.controller.admin;

import com.ra.md05_project.dto.news.NewsAddDTO;
import com.ra.md05_project.dto.news.NewsResponseDTO;
import com.ra.md05_project.dto.news.NewsUpdateDTO;
import com.ra.md05_project.dto.user.ResponseDTOSuccess;
import com.ra.md05_project.model.entity.ver1.Festival;
import com.ra.md05_project.model.entity.ver1.News;
import com.ra.md05_project.service.news.NewsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/admin/news")
public class ADNewsController {
    @Autowired
    private NewsService newsService;
    @GetMapping
    public ResponseEntity<Page<NewsResponseDTO>> findAllNews(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "3") int size,
            @RequestParam(name = "search", defaultValue = "") String search,
            @RequestParam(name = "sort", defaultValue = "id") String sort,
            @RequestParam(name = "direction", defaultValue = "asc") String direction
    ) {
        Sort sortOrder = Sort.by(sort);
        sortOrder = direction.equalsIgnoreCase("desc") ? sortOrder.descending() : sortOrder.ascending();
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        Page<NewsResponseDTO> newses = newsService.findAll(search ,pageable );
        return new ResponseEntity<>(newses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsResponseDTO> getNewsById (@PathVariable Long id) {
        return new ResponseEntity<>(newsService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<NewsResponseDTO> createNews(@Valid @ModelAttribute NewsAddDTO newsAddDTO) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(newsService.create(newsAddDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NewsResponseDTO> updateNews(@PathVariable Long id, @Valid @ModelAttribute NewsUpdateDTO newsUpdateDTO) throws IOException {
            return new ResponseEntity<>(newsService.update(id, newsUpdateDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNews(@PathVariable Long id) {
            newsService.delete(id);
            return new ResponseEntity<>(new ResponseDTOSuccess<>("Delete successfully",200),HttpStatus.OK);
    }
}
