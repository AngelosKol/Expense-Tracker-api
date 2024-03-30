package com.ang.rest.repositories;

import com.ang.rest.domain.entities.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

//    @Query("SELECT p FROM ProductEntity p WHERE p.transaction.id = :transactionId")
//    List<ProductEntity> findByTransactionId(@Param("transactionId") Long transactionId);
//
//    @Query("SELECT p FROM ProductEntity p WHERE p.transaction.id = :transactionId")
//    Page<ProductEntity> findByTransactionId(@Param("transactionId") Long transactionId, Pageable pageable);
//
//    @Modifying
//    @Query("DELETE FROM ProductEntity p WHERE p.transaction.id = :transactionId AND p.id = :productId")
//    void deleteProduct(@Param("transactionId") Long transactionId, @Param("productId") Long productId);
//

}
