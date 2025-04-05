package com.ang.rest.domain.dto;

import java.math.BigDecimal;


public record TransactionDetailsDTO(
         String name,
         BigDecimal price,
         BigDecimal quantity
         ) {

}
