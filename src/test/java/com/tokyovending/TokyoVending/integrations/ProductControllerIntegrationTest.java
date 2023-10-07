package com.tokyovending.TokyoVending.integrations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tokyovending.TokyoVending.dtos.ProductDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateProductAndRetrieveProduct() throws Exception {
        ProductDto productDto = new ProductDto();
        productDto.setName("TestProduct");
        productDto.setPrice(9.99);
        productDto.setSpecifications("Test specifications");

        String productDtoJson = objectMapper.writeValueAsString(productDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productDtoJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders.get("/products/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Transactional
    public void testUpdateProduct() throws Exception {
        ProductDto productDto = new ProductDto();
        productDto.setName("UpdateTestProduct");
        productDto.setPrice(19.99);
        productDto.setSpecifications("Update test specifications");

        String productDtoJson = objectMapper.writeValueAsString(productDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productDtoJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        productDto.setName("UpdatedProduct");
        String updatedProductDtoJson = objectMapper.writeValueAsString(productDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedProductDtoJson))
                .andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/products/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("UpdatedProduct"));
    }

    @Test
    @Transactional
    public void testDeleteProduct() throws Exception {
        ProductDto productDto = new ProductDto();
        productDto.setName("DeleteTestProduct");
        productDto.setPrice(29.99);
        productDto.setSpecifications("Delete test specifications");

        String productDtoJson = objectMapper.writeValueAsString(productDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productDtoJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders.delete("/products/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        mockMvc.perform(MockMvcRequestBuilders.get("/products/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testGetProductById_NotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/products/1000"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testDeleteProduct_NotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/products/1000"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}

