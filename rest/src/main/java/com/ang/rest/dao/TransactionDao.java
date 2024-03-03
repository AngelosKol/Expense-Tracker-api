package com.ang.rest.dao;

import com.ang.rest.domain.entities.TransactionEntity;

import java.util.List;
import java.util.Optional;

public interface TransactionDao {

    void create(TransactionEntity transaction);

    Optional<TransactionEntity> findOne(long id);

    List<TransactionEntity> find();

    void update(long id, TransactionEntity transaction);

    void delete(long id);
}
