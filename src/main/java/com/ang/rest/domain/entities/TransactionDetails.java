package com.ang.rest.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

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
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

    @Id
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;
    private BigDecimal price;

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof TransactionDetails)) return false;
        final TransactionDetails other = (TransactionDetails) o;
        if (!other.canEqual((Object) this)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof TransactionDetails;
    }

    public int hashCode() {
        int result = 1;
        return result;
    }
}
