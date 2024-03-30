package com.ang.rest.repositories;

import com.ang.rest.TestData;
import com.ang.rest.domain.entities.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TransactionRepositoryIntegrationTests {

    private TransactionRepository underTest;

    @Autowired
    public TransactionRepositoryIntegrationTests(TransactionRepository underTest){
        this.underTest = underTest;
    }

    @Test
    public void testThatTransactionCanBeCreatedAndRecalled(){
        Transaction transaction = TestData.createTestTransactionA();
        underTest.save(transaction);
        Optional<Transaction>  result = underTest.findById(transaction.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(transaction);
    }

    @Test
    public void testThatMultipleTransactionsCanBeCreatedAndRecalled(){
        Transaction transactionA = TestData.createTestTransactionA();
        underTest.save(transactionA);
        Transaction transactionB = TestData.createTestTransactionB();
        underTest.save(transactionB);
        Transaction transactionC = TestData.createTestTransactionC();
        underTest.save(transactionC);

        Iterable<Transaction> result = underTest.findAll();
        assertThat(result)
                .hasSize(3)
                .containsExactly(transactionA, transactionB, transactionC);
    }


    @Test
    public void testThatTransactionCanBeUpdated(){
        Transaction transaction = TestData.createTestTransactionB();
        underTest.save(transaction);
        transaction.setShop("Market in(updated)");
        underTest.save(transaction);
        Optional<Transaction>  result = underTest.findById(transaction.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(transaction);
    }

    @Test
    public void testThatTransactionCanBeDeleted(){
        Transaction transaction = TestData.createTestTransactionB();
        underTest.save(transaction);
        underTest.deleteById(transaction.getId());

        Optional<Transaction> result = underTest.findById(transaction.getId());
        assertThat(result).isEmpty();
    }

}
