package com.tokyovending.TokyoVending.services;

import com.tokyovending.TokyoVending.dtos.CategoryDto;
import com.tokyovending.TokyoVending.models.Product;
import com.tokyovending.TokyoVending.repositories.ProductRepository;
import com.tokyovending.TokyoVending.exceptions.RecordNotFoundException;
import com.tokyovending.TokyoVending.exceptions.EntityNotFoundException;
import com.tokyovending.TokyoVending.models.Category;
import com.tokyovending.TokyoVending.repositories.CategoryRepository;
import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
    private void initializeProducts(Category category) {
        Hibernate.initialize(category.getProducts());
    }

    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Transactional
    public CategoryDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() ->
                new RecordNotFoundException("Category with ID " + id + " not found.")
        );
        category.getProducts().size();
        return convertToDto(category);
    }

    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = convertToEntity(categoryDto);
        Category savedCategory = categoryRepository.save(category);
        return convertToDto(savedCategory);
    }

    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        Category existingCategory = categoryRepository.findById(id).orElseThrow(() ->
                new RecordNotFoundException("Category with ID " + id + " not found.")
        );
        updateEntityFromDto(existingCategory, categoryDto);
        Category updatedCategory = categoryRepository.save(existingCategory);
        return convertToDto(updatedCategory);
    }

    @Transactional
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new RecordNotFoundException("Category with ID " + id + " not found.");
        }
        categoryRepository.deleteById(id);
    }

    private CategoryDto convertToDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        categoryDto.setProducts(category.getProducts());
        return categoryDto;
    }

    private Category convertToEntity(CategoryDto categoryDto) {
        Category category = new Category();
        updateEntityFromDto(category, categoryDto);
        return category;
    }

    private void updateEntityFromDto(Category category, CategoryDto categoryDto) {
        category.setName(categoryDto.getName());
    }

    @Transactional
    public CategoryDto addProductToCategory(Long id, Long productId) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        product.setCategory(category);
        category.getProducts().add(product);

        Category updatedCategory = categoryRepository.save(category);
        return convertToDto(updatedCategory);
    }

    @Transactional
    public CategoryDto removeProductFromCategory(Long id, Long productId) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Category with ID " + id + " not found."));
        initializeProducts(category);
        boolean removed = category.getProducts().removeIf(product -> product.getId().equals(productId));

        if (!removed) {
            throw new RecordNotFoundException("Product with ID " + productId + " not found in Category with ID " + id);
        }

        Category updatedCategory = categoryRepository.save(category);
        return convertToDto(updatedCategory);
    }
}




