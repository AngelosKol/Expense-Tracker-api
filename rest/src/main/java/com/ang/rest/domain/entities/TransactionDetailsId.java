package com.ang.rest.domain.entities;


import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TransactionDetailsId implements Serializable {
    private Long transaction;
    private Long product;
}
