package com.ang.rest.domain.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;


@IdClass(TransactionDetailsId.class)
@Entity
@Table(name = "transaction_details")
public class TransactionDetails {


    @Id
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "transaction_id", nullable = false)
    public Transaction transaction;

    @Id
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    public Product product;

    public BigDecimal quantity;
    public BigDecimal price;


}
