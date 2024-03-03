package com.ang.rest.dao.impl;

import com.ang.rest.dao.ProductDao;
import com.ang.rest.domain.entities.ProductEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class ProductDaoImpl implements ProductDao {
    private  final JdbcTemplate jdbcTemplate;

    public ProductDaoImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void create(ProductEntity product) {
        jdbcTemplate.update(
                "INSERT INTO products (id, name, price) VALUES (?, ?, ?)",
                product.getId(), product.getName(), product.getPrice()
        );
    }

    @Override
    public Optional<ProductEntity> findOne(long id) {
        List<ProductEntity> results = jdbcTemplate.query(
                "SELECT id, name, price FROM products where id = ? LIMIT 1",
               new ProductRowMapper(), id);
                return results.stream().findFirst();
    }

    @Override
    public List<ProductEntity> find() {
        return null;
    }

    @Override
    public void update(long id, ProductEntity product) {

    }

    @Override
    public void delete(long id) {

    }

    public static class ProductRowMapper implements RowMapper<ProductEntity>{

        @Override
        public ProductEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            return ProductEntity.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .price(rs.getInt("price"))
                    .build();
        }
    }
}
