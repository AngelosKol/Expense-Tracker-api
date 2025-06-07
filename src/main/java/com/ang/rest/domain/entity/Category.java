package com.ang.rest.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_id_seq")
    @SequenceGenerator(name = "category_seq", sequenceName = "category_id_seq", allocationSize = 50)
    public Long id;

    @Column(unique = true, nullable = false)
    public String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public ProductFamily family;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<Product> products;

    public boolean equals(final Object o) {
        if(o == this) return true;
        if( o == null ||  getClass() != o.getClass()) return false;
        final Category other = (Category)  o;
        return Objects.equals(id, other.id);
    }
    public int hashCode() {
        final int prime = 59;
        int result = 1;
        result = result * prime + (id == null ? 43 : id.hashCode());
        return result;
    }
}
