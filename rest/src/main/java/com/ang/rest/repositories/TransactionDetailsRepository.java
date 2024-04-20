package com.ang.rest.repositories;

import com.ang.rest.domain.entities.TransactionDetails;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TransactionDetailsRepository extends CrudRepository<TransactionDetails, Long> {
    List<TransactionDetails> findByTransactionId(Long transactionId);


//    @Query("SELECT td.quantity, td.price, p.product_id, p.product_name FROM TransactionDetails td JOIN td.product p WHERE td.transaction.id = :transactionId")
//    List<Object[]> findProductDetailsByTransactionId
//            (@Param("transactionId") Long transactionId);

}
