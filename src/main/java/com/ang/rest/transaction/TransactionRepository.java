package com.ang.rest.transaction;

import com.ang.rest.BaseRepository;
import com.ang.rest.domain.entity.Transaction;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class TransactionRepository extends BaseRepository<Transaction, Long> {

    public boolean existsByShop_id(Long shopId) {
        return count("shop.id = :shopId", Parameters.with("shopId", shopId)) > 0;
    }
    public PanacheQuery<Transaction> findAllByUserId(Long userId) {
        return find("user.id = :userId", Parameters.with("userId", userId));
    }
    public PanacheQuery<Transaction>  findByIdAndUserId(Long transactionId, Long userId) {
        return find("id = :transactionId, user.id = :userId",
                Parameters.with("transactionId", transactionId).and("userId", userId)
        );
    }


}
