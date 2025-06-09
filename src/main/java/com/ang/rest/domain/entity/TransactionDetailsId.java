package com.ang.rest.domain.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;


@Embeddable
public class TransactionDetailsId implements Serializable {
    private Long transaction;
    private Long product;

    public TransactionDetailsId() {}

    public TransactionDetailsId(Long transaction, Long product) {
        this.transaction = transaction;
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionDetailsId that = (TransactionDetailsId) o;
        return Objects.equals(transaction, that.transaction) && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transaction, product);
    }
}
