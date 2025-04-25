package com.ang.rest.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Objects;


@Entity
@Table(name = "transaction")

public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_id_seq")
    @SequenceGenerator(name = "transaction_seq", sequenceName = "transaction_id_seq", allocationSize = 50)
    public Long id;

    public LocalDate date;
    @ManyToOne
    @JoinColumn(name = "shop_id", nullable = false)
    public Shop shop;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    public User user;


    @Override
    public String toString() {
        return "Transaction{" + "id=" + id + ", date='" + date + '\'' + '}';
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Transaction other = (Transaction) o;
        return Objects.equals(id, other.id);
    }
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.id;
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        return result;
    }
}
