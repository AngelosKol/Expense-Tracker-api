package com.ang.rest.repositories;

import com.ang.rest.domain.entities.TransactionDetails;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TransactionDetailsRepository extends CrudRepository<TransactionDetails, Long> {
    List<TransactionDetails> findByTransactionId(Long transactionId);


    @Modifying
    @Transactional
    @Query("DELETE FROM TransactionDetails td WHERE td.transaction.id = :transactionId AND td.product.id = :productId")
    void removeProductFromTransaction(@Param("transactionId") Long transactionId, @Param("productId") Long productId);

}
