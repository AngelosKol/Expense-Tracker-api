package com.ang.rest.domain.dto;

import com.ang.rest.domain.entity.ProductFamily;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoryDTO(
        Long id,
        @NotBlank(message = "Category name must not be blank")
        String name,
        @NotNull(message = "ProductFamily must not be null")
        ProductFamily productFamily
) {
}
