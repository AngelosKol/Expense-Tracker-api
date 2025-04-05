package com.ang.rest.domain.dto;
import java.math.BigDecimal;


public record ProductDetailsDTO(
        Long productId,
        BigDecimal price,
        BigDecimal quantity
) {

}
