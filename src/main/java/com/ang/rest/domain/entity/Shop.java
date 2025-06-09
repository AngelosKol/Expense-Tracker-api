package com.ang.rest.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "shop")
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shop_id_seq")
    @SequenceGenerator(name = "shop_seq", sequenceName = "shop_id_seq", allocationSize = 50)
    private Long id;

    @Column(unique = true)
    private String name;


    public boolean equals(final Object o) {
        if (o == this) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Shop other = (Shop) o;
        return Objects.equals(id, other.id);
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
