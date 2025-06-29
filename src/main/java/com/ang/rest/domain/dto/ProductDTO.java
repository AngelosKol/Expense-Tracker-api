package com.ang.rest.domain.dto;


import com.ang.rest.domain.entity.MeasuringType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductDTO(
        Long id,
        @NotBlank(message = "Name must not be blank")
        String name,
        String categoryName,
        Long categoryId,
        @NotNull(message = "MeasuringType must not be null")
        MeasuringType measuringType

) {

}