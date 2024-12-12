package com.ra.md05_project.service.news;

import com.ra.md05_project.dto.news.NewsAddDTO;
import com.ra.md05_project.dto.news.NewsResponseDTO;
import com.ra.md05_project.dto.news.NewsUpdateDTO;
import com.ra.md05_project.model.entity.ver1.Festival;
import com.ra.md05_project.model.entity.ver1.News;
import com.ra.md05_project.model.entity.ver1.NewsImage;
import com.ra.md05_project.repository.FestivalRepository;
import com.ra.md05_project.repository.NewsImagesRepository;
import com.ra.md05_project.repository.NewsRepository;
import com.ra.md05_project.service.uploadFile.UploadService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private FestivalRepository festivalRepository; // Đảm bảo rằng bạn có FestivalRepository nếu bạn sử dụng Festival
    @Autowired
    private NewsImagesRepository newsImagesRepository;
    @Autowired
    private UploadService uploadService;

    @Override
    public Page<NewsResponseDTO> findAll(String search, Pageable pageable) {
        Page<News> newsPage;
        if (search.isEmpty()) {
            newsPage = newsRepository.findAllByIsDeletedIsFalse(pageable);
        } else {
            newsPage = newsRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseAndIsDeletedIsFalse(search, search, pageable);
        }
        // Chuyển đổi từ News sang NewsResponseDTO
        return newsPage.map(this::convertToDTO);
    }


    @Override
    @Transactional
    public NewsResponseDTO create(NewsAddDTO newsAddDTO) throws IOException {
        Festival festival = null;
        if (newsAddDTO.getFestivalId() != null) {
            festival = festivalRepository.findById(newsAddDTO.getFestivalId())
                    .orElseThrow(() -> new NoSuchElementException("Festival not found"));
        }

        News news = new News();
        news.setTitle(newsAddDTO.getTitle());
        news.setContent(newsAddDTO.getContent());
        news.setIsDeleted(false);
        news.setFestival(festival);
        news.setCreatedAt(LocalDateTime.now());
        news.setUpdatedAt(LocalDateTime.now());
        news.setImages(new ArrayList<>());
        News temp = newsRepository.save(news);
        // Xử lý hình ảnh nếu có
        if (newsAddDTO.getImages() != null && !newsAddDTO.getImages().isEmpty()) {
            for (MultipartFile image : newsAddDTO.getImages()) {
                // Lưu ảnh và tạo NewsImage mới
                NewsImage newsImage = new NewsImage();
                newsImage.setImageUrl(uploadService.uploadFile(image)); // saveImage là phương thức tự viết để lưu ảnh
                newsImage.setNews(temp);
                newsImagesRepository.save(newsImage);
                temp.getImages().add(newsImage);
            }
        }

        return convertToDTO(newsRepository.save(temp));
    }

    @Override
    @Transactional
    public NewsResponseDTO update(Long id, NewsUpdateDTO newsUpdateDTO) throws IOException {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("News not found with id " + id));

        Festival festival = null;

        if (newsUpdateDTO.getFestivalId() != null) {
            festival = festivalRepository.findById(newsUpdateDTO.getFestivalId())
                    .orElseThrow(() -> new NoSuchElementException("Festival not found"));
        }

        news.setTitle(newsUpdateDTO.getTitle());
        news.setContent(newsUpdateDTO.getContent());
        news.setUpdatedAt(LocalDateTime.now());
        news.setFestival(festival);

        // Xử lý hình ảnh nếu có
        if (newsUpdateDTO.getImages() != null && !newsUpdateDTO.getImages().isEmpty()) {
            news.getImages().clear(); // Xóa hết hình ảnh cũ nếu có
            newsImagesRepository.deleteAllByNews_Id(news.getId());
            for (MultipartFile image : newsUpdateDTO.getImages()) {
                NewsImage newsImage = new NewsImage();
                newsImage.setImageUrl(uploadService.uploadFile(image));
                newsImage.setNews(news);
                newsImagesRepository.save(newsImage);
                news.getImages().add(newsImage);
            }
        }
        return convertToDTO(newsRepository.save(news));
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
    public NewsResponseDTO findById(Long id) {
        return convertToDTO(newsRepository.findById(id).orElseThrow(() -> new NoSuchElementException("News not found with id " + id)));
    }

    private NewsResponseDTO convertToDTO(News news) {
        NewsResponseDTO dto = new NewsResponseDTO();
        dto.setId(news.getId());
        dto.setTitle(news.getTitle());
        dto.setContent(news.getContent());
        dto.setFestivalId(news.getFestival() != null ? news.getFestival().getId() : null);
        dto.setCreatedAt(news.getCreatedAt());
        dto.setUpdatedAt(news.getUpdatedAt());

        // Lấy danh sách URL ảnh
        List<String> imageUrls = new ArrayList<>();
        for (NewsImage image : news.getImages()) {
            imageUrls.add(image.getImageUrl());
        }
        dto.setImagesUrl(imageUrls);

        return dto;
    }


}

