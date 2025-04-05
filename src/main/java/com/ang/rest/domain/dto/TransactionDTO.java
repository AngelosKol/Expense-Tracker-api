package com.ang.rest.domain.dto;

import java.time.LocalDate;

public record TransactionDTO(
         Long id,
         String shopName,
         LocalDate date
) {

}
