package com.ang.rest.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record ShopDTO(
         Long id,
         @NotBlank(message = "Shop name must not be null")
         String name
) {
    public ShopDTO withId(Long newId) {
        return new ShopDTO(newId, this.name);
    }
}
