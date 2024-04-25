package com.ang.rest.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDetailsDto {

//    private Long id;
//    private Long transactionId;
//    private Long productId;
    private Long productId;
    private String name;
    private int quantity;
    private BigDecimal price;



}
