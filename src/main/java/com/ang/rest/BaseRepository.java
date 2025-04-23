package com.ang.rest;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;

import java.util.List;

public abstract class BaseRepository<T, ID> implements PanacheRepositoryBase<T, ID> {

    public List<T> getAll() {
        return findAll().list();
    }
    public List<T> findAllByIds(List<ID> ids) {
        return find("id in ?1", ids).list();
    }

    public PanacheQuery<T> findByNameIgnoreCase(String name) {
        return find("lower(name) like ?1", "%" + name.toLowerCase() + "%");
    }
    public PanacheQuery<T> findByExactNameIgnoreCase(String name) {
        return find("lower(name) = ?1", "%" + name.toLowerCase() + "%");
    }
    public List<T> findAllByOrderByColumnAsc(String column) {
        String query = String.format("ORDER BY %s ASC", column);
        return find(query).list();
    }

    public boolean existsById(ID id) {
        return count("id = :id", Parameters.with("id", id)) > 0;

    }

    public boolean existsByName(String name) {
        return count("name = :name", Parameters.with("name", name)) > 0;
    }

}
