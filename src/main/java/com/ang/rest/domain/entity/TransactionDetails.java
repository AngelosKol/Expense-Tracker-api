package com.ang.rest.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@IdClass(TransactionDetailsId.class)
@Entity
@Table(name = "transaction_details")
public class TransactionDetails {


    @Id
    @ManyToOne
    @JoinColumn(name = "transaction_id", nullable = false)
    public Transaction transaction;

    @Id
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    public Product product;

    public BigDecimal quantity;
    public BigDecimal price;


}
