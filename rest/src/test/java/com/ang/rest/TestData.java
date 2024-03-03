package com.ang.rest;


import com.ang.rest.domain.entities.ProductEntity;
import com.ang.rest.domain.entities.TransactionEntity;

public class TestData {
    public static ProductEntity createTestProductA;

    private TestData(){};

    public static TransactionEntity createTestTransactionA(){
        return TransactionEntity.builder()
                .id(1L)
                .shop("Masoutis")
                .date("22/2/2024")
                .build();
    }
    public static TransactionEntity createTestTransactionB(){
        return TransactionEntity.builder()
                .id(2L)
                .shop("Kritikos")
                .date("26/03/2024")
                .build();
    }
    public static TransactionEntity createTestTransactionC(){
        return TransactionEntity.builder()
                .id(3L)
                .shop("Vasilopoulos")
                .date("03/03/2024")
                .build();
    }

    public static ProductEntity getCreateTestProductA(){
        return ProductEntity.builder()
                .id(1L)
                .name("Milk")
                .price(2)
                .build();
    }
    public static ProductEntity getCreateTestProductB(){
        return ProductEntity.builder()
                .id(2L)
                .name("Butter")
                .price(3)
                .build();
    }
    public static ProductEntity getCreateTestProductC(){
        return ProductEntity.builder()
                .id(1L)
                .name("Cheese")
                .price(4)
                .build();
    }
}
