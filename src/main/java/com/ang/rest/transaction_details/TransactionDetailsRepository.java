package com.ang.rest.transaction_details;

import com.ang.rest.domain.entity.TransactionDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface TransactionDetailsRepository extends JpaRepository<TransactionDetails, Long>, PagingAndSortingRepository<TransactionDetails, Long> {


    void deleteByTransactionId(Long transactionId);

    Optional<List<TransactionDetails>> findByTransactionId(Long transactionId);

    @EntityGraph(attributePaths = {"product"})
    Page<TransactionDetails> findByTransactionId(Long transactionId, Pageable pageable);

    boolean existsByProduct_id(Long id);

    @Modifying
    @Query("DELETE FROM TransactionDetails td WHERE td.transaction.id = :transactionId AND td.product.id IN (SELECT p.id FROM Product p WHERE p.name = :name)")
    void removeProductFromTransaction(@Param("transactionId") Long transactionId, @Param("name") String name);


}




