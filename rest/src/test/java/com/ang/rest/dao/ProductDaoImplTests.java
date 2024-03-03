package com.ang.rest.dao;
import com.ang.rest.TestData;
import com.ang.rest.dao.impl.ProductDaoImpl;
import com.ang.rest.dao.impl.TransactionDaoImpl;
import com.ang.rest.domain.entities.ProductEntity;
import com.ang.rest.domain.entities.TransactionEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ProductDaoImplTests {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private ProductDaoImpl underTest;


    @Test
    public void testThatCreateProductGeneratesCorrectSql(){
        ProductEntity product = TestData.getCreateTestProductA();
        underTest.create(product);
        verify(jdbcTemplate).update(
                eq("INSERT INTO products (id, name, price) VALUES (?, ?, ?)"),
                eq(1L), eq("Milk"), eq(2)
        );
    }

    @Test
    public void testThatFindOneGeneratesCorrectSql(){
      underTest.findOne(1L);
      verify(jdbcTemplate).query(
              eq("SELECT id, name, price FROM products where id = ? LIMIT 1"),
              ArgumentMatchers.<ProductDaoImpl.ProductRowMapper>any(),
              eq(1L)
      );

    }
}
