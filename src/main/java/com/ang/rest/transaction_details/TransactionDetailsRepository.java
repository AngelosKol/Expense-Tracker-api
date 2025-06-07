package com.ang.rest.transaction_details;

import com.ang.rest.BaseRepository;
import com.ang.rest.domain.entity.TransactionDetails;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class TransactionDetailsRepository extends BaseRepository<TransactionDetails, Long> {

    public PanacheQuery<TransactionDetails> findByTransactionId(Long transactionId) {
        return find("transaction.id = :transactionId", Parameters.with("transactionId", transactionId));
    }

    public boolean existsByProduct_id(Long productId) {
        return count("product.id = :productId", Parameters.with("productId", productId)) > 0;
    }

    public void removeProductFromTransaction(Long transactionId, String productName) {
         delete("transaction.id = :transactionId AND product.id IN (SELECT p.id FROM Product p where p.name = :productName)",
                Parameters.with("transactionId", transactionId).and("productName", productName));
    }

    public void deleteByTransactionId(Long transactionId) {
         delete("transaction.id = :transactionId", Parameters.with("transactionId", transactionId));
    }


}




