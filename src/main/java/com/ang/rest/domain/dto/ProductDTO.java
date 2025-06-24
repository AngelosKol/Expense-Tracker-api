package com.ang.rest.domain.dto;


import com.ang.rest.domain.entity.MeasuringType;

public record ProductDTO(
        Long id,
        String name,
        String categoryName,

        Long categoryId,

        MeasuringType measuringType

) {

}