package com.phdev.library.services;

import com.phdev.library.dto.CategoryRequest;
import com.phdev.library.dto.CategoryResponse;
import com.phdev.library.entities.Category;
import com.phdev.library.repositories.CategoryRepository;
import com.phdev.library.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public Page<CategoryResponse> findAll(Pageable pageable) {
        Page<Category> categories = categoryRepository.findAll(pageable);
        return categories.map(category -> new CategoryResponse(category));
    }

    @Transactional(readOnly = true)
    public CategoryResponse findById(Long id) {
        Category category = findByCategoryId(id);
        return new CategoryResponse(category);
    }

    @Transactional
    public CategoryResponse insert(CategoryRequest request) {
        Category category = new Category(null, request.name());
        categoryRepository.save(category);
        return new CategoryResponse(category);
    }

    @Transactional
    public CategoryResponse update(Long id, CategoryRequest request) {
        Category category = findByCategoryId(id);
        category.setName(request.name());
        categoryRepository.save(category);
        return new CategoryResponse(category);
    }

    @Transactional
    public void delete(Long id) {
        Category category = findByCategoryId(id);
        categoryRepository.delete(category);
    }

    private Category findByCategoryId(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        return category;
    }
}
