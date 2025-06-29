package com.ang.rest.mapper.impl;

import com.ang.rest.domain.dto.ProductDTO;
import com.ang.rest.domain.entity.Category;
import com.ang.rest.domain.entity.MeasuringType;
import com.ang.rest.domain.entity.Product;
import com.ang.rest.domain.entity.ProductFamily;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductMapperTest {
    private ProductMapper productMapper;

    static Stream<Arguments> provideProductsForMapping() {
        return Stream.of(
                Arguments.of(
                        new Product(1L, "Milk", new Category(10L, "Dairy", ProductFamily.DAIRY), MeasuringType.VOLUME),
                        1L, "Milk", "Dairy", 10L, MeasuringType.VOLUME
                ),
                Arguments.of(
                        new Product(2L, "Cheese", new Category(11L, "Cheeses", ProductFamily.DAIRY), MeasuringType.WEIGHT),
                        2L, "Cheese", "Cheeses", 11L, MeasuringType.WEIGHT
                ),
                Arguments.of(
                        new Product(3L, "Apple", null, MeasuringType.UNIT),
                        3L, "Apple", null, null, MeasuringType.UNIT
                )
        );
    }

    static Stream<Arguments> provideDtosForMapping() {
        return Stream.of(
                Arguments.of(
                        new ProductDTO(1L, "Milk", "Dairy", 10L, MeasuringType.VOLUME),
                        new Category(10L, "Dairy", ProductFamily.DAIRY),
                        1L, "Milk", new Category(10L, "Dairy", ProductFamily.DAIRY), MeasuringType.VOLUME
                ),
                Arguments.of(
                        new ProductDTO(2L, "Cheese", "Cheeses", 11L, MeasuringType.WEIGHT),
                        new Category(11L, "Cheeses", ProductFamily.DAIRY),
                        2L, "Cheese", new Category(11L, "Cheeses", ProductFamily.DAIRY), MeasuringType.WEIGHT
                ),
                Arguments.of(
                        new ProductDTO(3L, "Apple", null, null, MeasuringType.UNIT),
                        null,
                        3L, "Apple", null, MeasuringType.UNIT
                )
        );
    }

    @BeforeEach
    void setUp() {
        productMapper = new ProductMapper();
    }


    @ParameterizedTest
    @MethodSource("provideProductsForMapping")
    void mapToDto_shouldMapCorrectly(Product product, Long expectedId, String expectedName,
                                     String expectedCategoryName, Long expectedCategoryId, MeasuringType expectedType) {
        ProductDTO dto = productMapper.mapToDto(product);

        assertEquals(expectedId, dto.id());
        assertEquals(expectedName, dto.name());
        assertEquals(expectedCategoryName, dto.categoryName());
        assertEquals(expectedCategoryId, dto.categoryId());
        assertEquals(expectedType, dto.measuringType());
    }


    @ParameterizedTest
    @MethodSource("provideDtosForMapping")
    void mapToEntity_shouldMapCorrectly(ProductDTO dto, Category category, Long expectedId, String expectedName, Category expectedCategory, MeasuringType expectedType) {
        Product product = productMapper.mapToEntity(dto, category);
        assertEquals(expectedId, product.getId());
        assertEquals(expectedName, product.getName());
        assertEquals(expectedCategory, product.getCategory());
        assertEquals(expectedType, product.getMeasuringType());
    }

}