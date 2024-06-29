package com.ang.rest.repositories;

import com.ang.rest.domain.entities.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long>,
        PagingAndSortingRepository<Transaction, Long> {

boolean existsByShop_id(Long id);


}
