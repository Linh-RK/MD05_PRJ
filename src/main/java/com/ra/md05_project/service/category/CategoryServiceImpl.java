package com.ra.md05_project.service.category;


import com.ra.md05_project.dto.category.CategoryDTO;
import com.ra.md05_project.exception.CustomException;
import com.ra.md05_project.model.entity.ver1.Category;
import com.ra.md05_project.repository.CategoryRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.NoSuchElementException;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Page<Category> findAll(String search, Pageable pageable) {
        if (search == null || search.isEmpty()) {
            return categoryRepository.findAll(pageable);
        }
        return categoryRepository.findAllByCategoryNameContainingIgnoreCase(search, pageable);
    }

    @Override
    public void delete(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Category with id " + id + " not found"));

        if (category.getMovies() == null || category.getMovies().isEmpty()) {
            categoryRepository.delete(category);
        } else {
            throw new CustomException("Cannot delete category with id " + id + " because it contains movies.");
        }
    }

    @Override
    public Category create( CategoryDTO categoryDTO) {

        Category newCategory = Category.builder()
                .categoryName(categoryDTO.getCategoryName())
                .movies(new ArrayList<>())
                .isDeleted(false)
                .build();

        return categoryRepository.save(newCategory);
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Category with id " + id + " not found"));
    }

    @Override
    public Category update(Long id, Category category) {
        if (category.getCategoryName() == null || category.getCategoryName().isBlank()) {
            throw new IllegalArgumentException("Category name cannot be null or blank.");
        }

        Category existingCategory = categoryRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Category with id " + id + " not found"));

        existingCategory.setCategoryName(category.getCategoryName());
        return categoryRepository.save(existingCategory);
    }
}

