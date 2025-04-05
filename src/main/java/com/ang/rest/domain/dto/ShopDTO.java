package com.ang.rest.domain.dto;

public record ShopDTO(
         Long id,
         String name
) {
    public ShopDTO withId(Long newId) {
        return new ShopDTO(newId, this.name);
    }
}
