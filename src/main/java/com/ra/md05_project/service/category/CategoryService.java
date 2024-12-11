package com.ra.md05_project.service.category;

import com.ra.md05_project.dto.category.CategoryDTO;
import com.ra.md05_project.model.entity.ver1.Category;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    Page<Category> findAll(String search, Pageable pageable);

    void delete(Long id);

    Category create(@Valid CategoryDTO category);

    Category findById(Long id);

    Category update(Long id, Category category);
}
