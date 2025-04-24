package com.ang.rest.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;





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


}
