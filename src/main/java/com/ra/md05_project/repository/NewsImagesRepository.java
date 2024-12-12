package com.ra.md05_project.repository;

import com.ra.md05_project.model.entity.ver1.NewsImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsImagesRepository extends JpaRepository<NewsImage,Long> {
    void deleteAllByNews_Id(Long newsId);
}
