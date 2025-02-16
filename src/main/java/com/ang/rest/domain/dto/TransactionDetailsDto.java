package com.ang.rest.domain.dto;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class TransactionDetailsDto {
    private String name;
    private BigDecimal quantity;
    private BigDecimal price;
}
