package com.ang.rest.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "transaction_details")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@IdClass(TransactionDetailsId.class)

public class TransactionDetails {


    @Id
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

    @Id
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;
    private BigDecimal price;

}
