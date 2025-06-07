package com.ang.rest.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product  {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_seq")
    public Long id;

    @Column(unique = true, nullable = false)
    public String name;


    @ManyToOne
    @JoinColumn(name = "category_id")
    public Category category;

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", name='" + name + '\'' + '}';
    }

    @Override
    public boolean equals(final Object o) {
        if(o == this) return true;
        if( o == null ||  getClass() != o.getClass()) return false;
        final Product other = (Product)  o;
        return Objects.equals(id, other.id);
    }
    @Override
    public int hashCode() {
        final int prime = 59;
        int result = 1;
        result = result * prime + (id == null ? 43 : id.hashCode());
        return result;
    }
}
