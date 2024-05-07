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

public class TransactionDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_details_seq")
    @EqualsAndHashCode.Include
    private Long id;


    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;


    @ManyToOne
    @JoinColumn(name = "product_id" )
    private Product product;

    private int quantity;
    private BigDecimal price;

}
