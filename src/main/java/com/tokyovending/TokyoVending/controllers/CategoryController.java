package com.tokyovending.TokyoVending.controllers;

import com.tokyovending.TokyoVending.dtos.CategoryDto;
import com.tokyovending.TokyoVending.exceptions.RecordNotFoundException;
import com.tokyovending.TokyoVending.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id) {
        CategoryDto categoryDto = categoryService.getCategoryById(id);
        if (categoryDto != null) {
            return ResponseEntity.ok(categoryDto);
        } else {
            throw new RecordNotFoundException("Category with ID " + id + " not found.");
        }
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto createdCategoryDto = categoryService.createCategory(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategoryDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto updatedCategoryDto = categoryService.updateCategory(id, categoryDto);
        if (updatedCategoryDto != null) {
            return ResponseEntity.ok(updatedCategoryDto);
        } else {
            throw new RecordNotFoundException("Category with ID " + id + " not found.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{categoryId}/add-product/{productId}")
    public ResponseEntity<CategoryDto> addProductToCategory(@PathVariable Long categoryId, @PathVariable Long productId) {
        CategoryDto updatedCategoryDto = categoryService.addProductToCategory(categoryId, productId);
        return ResponseEntity.ok(updatedCategoryDto);
    }

    @DeleteMapping("/{categoryId}/remove-product/{productId}")
    public ResponseEntity<CategoryDto> removeProductFromCategory(@PathVariable Long categoryId, @PathVariable Long productId) {
        CategoryDto updatedCategoryDto = categoryService.removeProductFromCategory(categoryId, productId);
        if (updatedCategoryDto == null) {
            throw new RecordNotFoundException("Failed to remove product with ID " + productId + " from Category with ID " + categoryId);
        }
        return ResponseEntity.ok(updatedCategoryDto);
    }

}


