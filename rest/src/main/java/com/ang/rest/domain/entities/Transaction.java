package com.ang.rest.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "transactions")

public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_id_seq")
    @EqualsAndHashCode.Include
    private Long id;

    private String shop;

    private String date;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "transaction_products",
            joinColumns = @JoinColumn(name = "transaction_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<Product> products;

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", shop='" + shop + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
