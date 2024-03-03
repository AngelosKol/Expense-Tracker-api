package com.ang.rest.dao;
import com.ang.rest.TestData;
import com.ang.rest.dao.impl.TransactionDaoImpl;
import com.ang.rest.domain.entities.TransactionEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TransactionDaoImplTests {
    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private TransactionDaoImpl underTest;


    @Test
    public void testThatCreateTransactionGeneratesCorrectSql(){
        TransactionEntity transaction = TestData.createTestTransactionA();
        underTest.create(transaction);
        verify(jdbcTemplate).update(
                eq("INSERT INTO transactions (id, shop, date) VALUES (?, ?, ?)"),
                eq(1L), eq("Masoutis"),eq("22/2/2024")
        );
    }

    @Test
    public void testThatFindOneGeneratesCorrectSql(){
        underTest.findOne(1L);
        verify(jdbcTemplate).query(
                eq("SELECT id, shop, date FROM transactions where id = ? LIMIT 1"),
                ArgumentMatchers.<TransactionDaoImpl.TransactionRowMapper>any(),
                eq(1L)
        );
    }

    @Test
    public void testThatFindManyGeneratesCorrectSql(){
        underTest.find();
        verify(jdbcTemplate).query(
                eq("SELECT id, shop, date from transactions"),
                ArgumentMatchers.<TransactionDaoImpl.TransactionRowMapper>any()
        );
    }

    @Test
    public void testThatUpdateGeneratesCorrectSql(){
        TransactionEntity transaction = TestData.createTestTransactionB();
        underTest.update(5L, transaction);
        verify(jdbcTemplate).update(
                "UPDATE transactions set id = ?, shop = ?, date = ? WHERE id = ?",
                2L,"Kritikos", "26/03/2024", 5L
        );
    }

    @Test
    public void testThatDeleteGeneratesCorrectSql(){
        TransactionEntity transaction = TestData.createTestTransactionC();
        underTest.delete(transaction.getId());
        verify(jdbcTemplate).update(
                "DELETE FROM transactions where id = ?",
                3L
        );
    }
}
