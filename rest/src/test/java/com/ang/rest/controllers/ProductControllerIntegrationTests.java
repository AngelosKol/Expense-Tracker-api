package com.ang.rest.controllers;

import com.ang.rest.TestData;
import com.ang.rest.domain.dto.ProductDto;
import com.ang.rest.domain.entities.ProductEntity;
import com.ang.rest.domain.entities.TransactionEntity;
import com.ang.rest.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class ProductControllerIntegrationTests {

    private ObjectMapper objectMapper;
    private MockMvc mockMvc;
    private ProductService productService;

    @Autowired
    public ProductControllerIntegrationTests(ObjectMapper objectMapper, MockMvc mockMvc, ProductService productService) {
        this.objectMapper = objectMapper;
        this.mockMvc = mockMvc;
        this.productService = productService;
    }

    @Test
    public void testThatCreateProductSuccessfullyReturnsHttp201Created() throws Exception {
        ProductDto product = TestData.createTestProductDTOA(null);
        product.setId(null);
        String productJson = objectMapper.writeValueAsString(product);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }
    @Test
    public void testThatCreateProductSuccessfullyReturnsSavedProduct() throws Exception {
        ProductDto product = TestData.createTestProductDTOA(null);
        product.setId(null);
        String productJson = objectMapper.writeValueAsString(product);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(product.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.price").value(product.getPrice())
        );
    }

    @Test
    public void testThatGetProductsReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/products")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatGetProductsReturnsListOfProducts() throws  Exception{
        ProductEntity product = TestData.createProductEntityA(null);
        productService.createProduct(product);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/products")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()

        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value("Milk")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].price").value(2)
        );
    }

    @Test
    public  void testThatGetProductReturnsHttpStatus200WhenProductExists() throws Exception {
        ProductEntity product = TestData.createProductEntityA(null);
        productService.createProduct(product);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/products/" + product.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());

    }
    @Test
    public  void testThatGetProductReturnsHttpStatus400WhenProductNotExists() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());

    }
    @Test
    public  void testThatGetProductReturnsProductWhenProductExists() throws Exception {
        ProductEntity product = TestData.createProductEntityA(null);
        productService.createProduct(product);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/products/" + product.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Milk")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.price").value(2)
        );

    }
}
