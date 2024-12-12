package com.ra.md05_project.service.category;


import com.ra.md05_project.dto.category.CategoryDTO;
import com.ra.md05_project.dto.category.CategoryResponseDTO;
import com.ra.md05_project.exception.CustomException;
import com.ra.md05_project.model.entity.ver1.Category;
import com.ra.md05_project.model.entity.ver1.Movie;
import com.ra.md05_project.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // Hàm chuyển đổi từ Category sang CategoryResponseDTO
    private CategoryResponseDTO convertToCategoryResponseDTO(Category category) {
        // Giả sử rằng mỗi category có một danh sách các movie ID
        List<Long> movieIds = category.getMovies() != null ? category.getMovies().stream()
                .map( Movie::getId) // Lấy ID của các movie liên quan
                .collect(Collectors.toList()) : new ArrayList<>();

        return CategoryResponseDTO.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .isDeleted(category.getIsDeleted())
                .movies(movieIds)
                .build();
    }

    @Override
    public Page<CategoryResponseDTO> findAll(String search, Pageable pageable) {
        Page<Category> categories;
        if (search == null || search.isEmpty()) {
            categories = categoryRepository.findAll(pageable);
        } else {
            categories = categoryRepository.findAllByCategoryNameContainingIgnoreCase(search, pageable);
        }

        // Chuyển đổi Page<Category> thành Page<CategoryResponseDTO>
        return categories.map(this::convertToCategoryResponseDTO);
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
    public CategoryResponseDTO create(CategoryDTO categoryDTO) {
        Category newCategory = Category.builder()
                .categoryName(categoryDTO.getCategoryName())
                .movies(new ArrayList<>()) // Khởi tạo danh sách movies rỗng
                .isDeleted(false)
                .build();

        newCategory = categoryRepository.save(newCategory);

        return convertToCategoryResponseDTO(newCategory);  // Chuyển đổi sang CategoryResponseDTO
    }

    @Override
    public CategoryResponseDTO findById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Category with id " + id + " not found"));

        return convertToCategoryResponseDTO(category);  // Chuyển đổi sang CategoryResponseDTO
    }

    @Override
    public CategoryResponseDTO update(Long id, Category category) {
        if (category.getCategoryName() == null || category.getCategoryName().isBlank()) {
            throw new IllegalArgumentException("Category name cannot be null or blank.");
        }

        Category existingCategory = categoryRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Category with id " + id + " not found"));

        existingCategory.setCategoryName(category.getCategoryName());
        existingCategory = categoryRepository.save(existingCategory);

        return convertToCategoryResponseDTO(existingCategory);  // Chuyển đổi sang CategoryResponseDTO
    }
}


