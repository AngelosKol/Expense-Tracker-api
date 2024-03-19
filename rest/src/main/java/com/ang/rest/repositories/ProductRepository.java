package com.ang.rest.repositories;

import com.ang.rest.domain.entities.ProductEntity;
import com.ang.rest.domain.entities.TransactionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<ProductEntity,Long>,
        PagingAndSortingRepository<ProductEntity,Long> {

    @Query("SELECT p FROM ProductEntity p WHERE p.transaction.id = :transactionId")
    List<ProductEntity> findByTransactionId(@Param("transactionId") Long transactionId);

    @Query("SELECT p FROM ProductEntity p WHERE p.transaction.id = :transactionId")
    Page<ProductEntity> findByTransactionId(@Param("transactionId") Long transactionId, Pageable pageable);



    @Modifying
    @Query("DELETE FROM ProductEntity p WHERE p.transaction.id = :transactionId AND p.id = :productId")
    void deleteProduct(@Param("transactionId") Long transactionId, @Param("productId") Long productId);


}
