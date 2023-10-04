package com.tokyovending.TokyoVending.services;

import com.tokyovending.TokyoVending.dtos.ProductDto;
import com.tokyovending.TokyoVending.exceptions.RecordNotFoundException;
import com.tokyovending.TokyoVending.models.Product;
import com.tokyovending.TokyoVending.repositories.ProductRepository;
import com.tokyovending.TokyoVending.utils.ProductConverter;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public ProductDto getProductDtoById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() ->
                new RecordNotFoundException("Product with ID " + id + " not found.")
        );
        Hibernate.initialize(product.getCategory());
        Hibernate.initialize(product.getVendingMachine());
        return ProductConverter.toDto(product);
    }


    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() ->
                new RecordNotFoundException("Product with ID " + id + " not found.")
        );
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product product) {
        Product existingProduct = getProductById(id);
        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setSpecifications(product.getSpecifications());
        return productRepository.save(existingProduct);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RecordNotFoundException("Product with ID " + id + " not found.");
        }
        productRepository.deleteById(id);
    }
}



