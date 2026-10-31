package com.phdev.library.dto;

import com.phdev.library.entities.Category;

public record CategoryResponse(
        Long id,
        String name
) {

    public CategoryResponse(Category category) {
        this(category.getId(), category.getName());
    }
}
