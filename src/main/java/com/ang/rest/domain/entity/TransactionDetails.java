package com.ang.rest.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "transaction_details")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@IdClass(TransactionDetailsId.class)

public class TransactionDetails {


    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id", nullable = false)
    private Transaction transaction;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private BigDecimal quantity;
    private BigDecimal price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionDetails that = (TransactionDetails) o;
        return Objects.equals(getTransaction(), that.getTransaction()) && Objects.equals(getProduct(), that.getProduct());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTransaction(), getProduct());
    }
}
