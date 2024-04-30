package com.ang.rest;


import com.ang.rest.domain.dto.ProductDto;
import com.ang.rest.domain.dto.TransactionPostDto;
import com.ang.rest.domain.entities.Product;
import com.ang.rest.domain.entities.Transaction;

public class TestData {
    public static Product createTestProductA;

    private TestData(){};

    public static Transaction createTestTransactionA(){
        return Transaction.builder()
                .id(1L)
                .shop("Masoutis")
                .date("22/2/2024")
                .build();
    }
    public static Transaction createTestTransactionB(){
        return Transaction.builder()
                .id(2L)
                .shop("Kritikos")
                .date("26/03/2024")
                .build();
    }
    public static Transaction createTestTransactionC(){
        return Transaction.builder()
                .id(3L)
                .shop("Vasilopoulos")
                .date("03/03/2024")
                .build();
    }

    public static Product createProductEntityA(final Transaction transaction){
        return Product.builder()
                .id(1L)
                .name("Milk")
                .price(2)
//                .transactions(transaction)
                .build();
    }
    public static ProductDto createTestProductDTOA(final TransactionPostDto transactionPostDto){
        return ProductDto.builder()
                .id(2L)
                .name("Butter")
                .price(3)
//                .transactions(transactionDto)
                .build();
    }
    public static Product createTestProductC(){
        return Product.builder()
                .id(1L)
                .name("Cheese")
                .price(4)
                .build();
    }
}
