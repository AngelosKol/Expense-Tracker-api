package com.ang.rest.transaction;

import com.ang.rest.domain.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>, PagingAndSortingRepository<Transaction, Long> {

    @EntityGraph(attributePaths = {"shop"})
    List<Transaction> findAllByUserId(Long userId);

    @EntityGraph(attributePaths = {"shop"})
    Page<Transaction> findAllByUserId(Long userId, Pageable pageable);

    Optional<Transaction> findByIdAndUserId(Long transactionId, Long userId);

    boolean existsByShop_id(Long id);


}
