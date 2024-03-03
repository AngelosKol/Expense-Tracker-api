package com.ang.rest.repositories;

import com.ang.rest.TestData;
import com.ang.rest.domain.entities.TransactionEntity;
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
        TransactionEntity transactionEntity = TestData.createTestTransactionA();
        underTest.save(transactionEntity);
        Optional<TransactionEntity>  result = underTest.findById(transactionEntity.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(transactionEntity);
    }

    @Test
    public void testThatMultipleTransactionsCanBeCreatedAndRecalled(){
        TransactionEntity transactionEntityA = TestData.createTestTransactionA();
        underTest.save(transactionEntityA);
        TransactionEntity transactionEntityB = TestData.createTestTransactionB();
        underTest.save(transactionEntityB);
        TransactionEntity transactionEntityC = TestData.createTestTransactionC();
        underTest.save(transactionEntityC);

        Iterable<TransactionEntity> result = underTest.findAll();
        assertThat(result)
                .hasSize(3)
                .containsExactly(transactionEntityA,transactionEntityB,transactionEntityC);
    }


    @Test
    public void testThatTransactionCanBeUpdated(){
        TransactionEntity transactionEntity = TestData.createTestTransactionB();
        underTest.save(transactionEntity);
        transactionEntity.setShop("Market in(updated)");
        underTest.save(transactionEntity);
        Optional<TransactionEntity>  result = underTest.findById(transactionEntity.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(transactionEntity);
    }

    @Test
    public void testThatTransactionCanBeDeleted(){
        TransactionEntity transaction = TestData.createTestTransactionB();
        underTest.save(transaction);
        underTest.deleteById(transaction.getId());

        Optional<TransactionEntity> result = underTest.findById(transaction.getId());
        assertThat(result).isEmpty();
    }

}
