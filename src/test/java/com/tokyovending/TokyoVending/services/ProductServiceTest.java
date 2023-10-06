package com.tokyovending.TokyoVending.services;

import com.tokyovending.TokyoVending.exceptions.RecordNotFoundException;
import com.tokyovending.TokyoVending.models.Product;
import com.tokyovending.TokyoVending.repositories.ProductRepository;
import com.tokyovending.TokyoVending.dtos.ProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    private Product mockProduct1;
    private Product mockProduct2;

    @BeforeEach
    void setUp() {
        mockProduct1 = new Product();
        mockProduct1.setId(1L);
        mockProduct1.setName("Test Product 1");

        mockProduct2 = new Product();
        mockProduct2.setId(2L);
        mockProduct2.setName("Test Product 2");
    }

    @Test
    void getProductById_existingId_shouldReturnProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(mockProduct1));
        Product retrievedProduct = productService.getProductById(1L);
        assertNotNull(retrievedProduct);
        assertEquals("Test Product 1", retrievedProduct.getName());
    }

    @Test
    void getProductById_nonExistingId_shouldThrowException() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RecordNotFoundException.class, () -> productService.getProductById(1L));
    }

    @Test
    void getAllProducts_whenProductsExist_shouldReturnAllProducts() {
        when(productRepository.findAll()).thenReturn(List.of(mockProduct1, mockProduct2));
        List<Product> products = productService.getAllProducts();
        assertNotNull(products);
        assertEquals(2, products.size());
        assertEquals("Test Product 1", products.get(0).getName());
        assertEquals("Test Product 2", products.get(1).getName());
    }

    @Test
    void getProductDtoById_existingId_shouldReturnProductDto() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(mockProduct1));
        ProductDto productDto = productService.getProductDtoById(1L);
        assertNotNull(productDto);
        assertEquals("Test Product 1", productDto.getName());
    }

    @Test
    void getProductDtoById_nonExistingId_shouldThrowException() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RecordNotFoundException.class, () -> productService.getProductDtoById(1L));
    }

    @Test
    void createProduct_validProduct_shouldReturnSavedProduct() {
        Product productToSave = new Product();
        productToSave.setName("New Product");
        when(productRepository.save(productToSave)).thenReturn(productToSave);
        Product savedProduct = productService.createProduct(productToSave);
        assertNotNull(savedProduct);
        assertEquals("New Product", savedProduct.getName());
    }

    @Test
    void updateProduct_existingId_shouldReturnUpdatedProduct() {
        Product existingProduct = new Product();
        existingProduct.setId(1L);
        existingProduct.setName("Old Product");

        Product newProductData = new Product();
        newProductData.setName("Updated Product");

        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(existingProduct)).thenReturn(existingProduct);

        Product updatedProduct = productService.updateProduct(1L, newProductData);
        assertNotNull(updatedProduct);
        assertEquals("Updated Product", updatedProduct.getName());
    }

    @Test
    void updateProduct_nonExistingId_shouldThrowException() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RecordNotFoundException.class, () -> {
            Product newProductData = new Product();
            newProductData.setName("Updated Product");
            productService.updateProduct(1L, newProductData);
        });
    }

    @Test
    void deleteProduct_existingId_shouldDeleteProduct() {
        when(productRepository.existsById(1L)).thenReturn(true);
        productService.deleteProduct(1L);
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteProduct_nonExistingId_shouldThrowException() {
        when(productRepository.existsById(1L)).thenReturn(false);
        assertThrows(RecordNotFoundException.class, () -> productService.deleteProduct(1L));
    }
}


