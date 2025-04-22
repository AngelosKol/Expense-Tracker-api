package com.ang.rest.category;

import com.ang.rest.BaseRepository;
import com.ang.rest.domain.entity.Category;
import io.quarkus.panache.common.Parameters;

public class CategoryRepository extends BaseRepository<Category, Long> {

    public  Category findByNameLight(String name) {
        return find("SELECT new com.ang.rest.domain.entity.Category(id, name, family) WHERE name = :name",
                Parameters.with("name", name))
                .firstResult();
    }
}


