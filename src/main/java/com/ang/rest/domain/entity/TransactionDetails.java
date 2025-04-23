package com.ang.rest.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@IdClass(TransactionDetailsId.class)
@Entity
@Table(name = "transaction_details")
public class TransactionDetails {


    @Id
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "transaction_id", nullable = false)
    private Transaction transaction;

    @Id
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private BigDecimal quantity;
    private BigDecimal price;


}
