package com.ang.rest.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;


@Entity
@Table(name = "shop")
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shop_id_seq")
    @SequenceGenerator(name = "shop_seq", sequenceName = "shop_id_seq", allocationSize = 50)
    public Long id;

    @Column(unique = true)
    public String name;


    public boolean equals(final Object o) {
        if (o == this) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shop other = (Shop)  o;
        return Objects.equals(id, other.id);
    }

    public int hashCode() {
        final int prime = 59;
        int result = 1;
        result = result * prime + (id == null ? 43 : id.hashCode());
        return result;
    }
}
