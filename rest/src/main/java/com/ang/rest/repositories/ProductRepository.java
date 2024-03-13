package com.ang.rest.repositories;

import com.ang.rest.domain.entities.ProductEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<ProductEntity,Long> {

    @Query("SELECT p FROM ProductEntity p WHERE p.transaction.id = :transactionId")
    List<ProductEntity> findByTransactionId(@Param("transactionId") Long transactionId);
}
