package com.phdev.library.controller;

import com.phdev.library.dto.CategoryRequest;
import com.phdev.library.dto.CategoryResponse;
import com.phdev.library.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<Page<CategoryResponse>> findAll(Pageable pageable) {
        Page<CategoryResponse> categoryResponses = categoryService.findAll(pageable);
        return ResponseEntity.ok().body(categoryResponses);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryResponse> findById(@PathVariable Long id) {
        CategoryResponse categoryResponse = categoryService.findById(id);
        return ResponseEntity.ok().body(categoryResponse);
    }

    @GetMapping(params = "name")
    public ResponseEntity<Page<CategoryResponse>> findByName(@RequestParam(name = "name") String name, Pageable pageable) {
        Page<CategoryResponse> categoryResponses =  categoryService.findByName(name, pageable);
        return ResponseEntity.ok().body(categoryResponses);
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> insert(@Valid @RequestBody CategoryRequest request) {
        CategoryResponse categoryResponse = categoryService.insert(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(categoryResponse.id()).toUri();
        return ResponseEntity.created(uri).body(categoryResponse);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CategoryResponse> update(@PathVariable Long id,
                                                   @Valid @RequestBody CategoryRequest request) {
        CategoryResponse categoryResponse = categoryService.update(id, request);
        return ResponseEntity.ok().body(categoryResponse);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
