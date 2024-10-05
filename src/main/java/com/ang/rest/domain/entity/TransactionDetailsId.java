package com.ang.rest.domain.entity;


import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public class TransactionDetailsId implements Serializable {
    private Long transaction;
    private Long product;
}
