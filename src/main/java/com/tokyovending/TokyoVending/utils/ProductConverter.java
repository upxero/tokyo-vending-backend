package com.tokyovending.TokyoVending.utils;

import com.tokyovending.TokyoVending.dtos.ProductDto;
import com.tokyovending.TokyoVending.models.Product;

public class ProductConverter {

    public static ProductDto toDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setSpecifications(product.getSpecifications());

        if (product.getCategory() != null) {
            productDto.setCategoryId(product.getCategory().getId());
        }
        if (product.getVendingMachine() != null) {
            productDto.setVendingMachineId(product.getVendingMachine().getId());
        }
        return productDto;
    }
}

