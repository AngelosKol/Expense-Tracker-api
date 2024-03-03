package com.ang.rest.dao.impl;

import com.ang.rest.dao.TransactionDao;
import com.ang.rest.domain.entities.TransactionEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import java.util.List;
import java.util.Optional;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
@Component
public class TransactionDaoImpl implements TransactionDao {

    private  final JdbcTemplate jdbcTemplate;

    public TransactionDaoImpl(final JdbcTemplate jdbcTemplate){ this.jdbcTemplate = jdbcTemplate;}
    @Override
    public void create(TransactionEntity transaction) {
        jdbcTemplate.update(
                "INSERT INTO transactions (id, shop, date) VALUES (?, ?, ?)",
                transaction.getId(), transaction.getShop(), transaction.getDate()
        );
    }

    @Override
    public Optional<TransactionEntity> findOne(long id) {
    List<TransactionEntity> results =  jdbcTemplate.query(
                "SELECT id, shop, date FROM transactions where id = ? LIMIT 1",
                new TransactionRowMapper(),id);
        return results.stream().findFirst();
    }

    @Override
    public List<TransactionEntity> find() {
     return jdbcTemplate.query(
                "SELECT id, shop, date from transactions",
                new TransactionRowMapper()
        );
    }

    @Override
    public void update(long id, TransactionEntity transaction) {
         jdbcTemplate.update(
                "UPDATE transactions set id = ?, shop = ?, date = ? WHERE id = ?",
                transaction.getId(), transaction.getShop(), transaction.getDate(), id
        );

    }

    @Override
    public void delete(long id) {
        jdbcTemplate.update(
                "DELETE FROM transactions where id = ?",
                id
        );

    }

    public static class TransactionRowMapper implements  RowMapper<TransactionEntity>{

        @Override
        public TransactionEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            return TransactionEntity.builder()
                    .id(rs.getLong("id"))
                    .shop(rs.getString("shop"))
                    .date(rs.getString("date"))
                    .build();
        }
    }
}
