package com.ang.rest.category;

import com.ang.rest.BaseRepository;
import com.ang.rest.domain.entity.Category;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CategoryRepository extends BaseRepository<Category, Long> {

    public PanacheQuery<Category> findByNameLight(String name) {
        return find("SELECT new com.ang.rest.domain.entity.Category(id, name, family) WHERE name = :name",
                Parameters.with("name", name));
    }
}


