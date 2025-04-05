package com.ang.rest;


import com.ang.rest.domain.entity.Product;
import com.ang.rest.domain.entity.Shop;
import com.ang.rest.domain.entity.Transaction;

public class TestData {
    public static Product createTestProductA;

    private TestData(){};


    public static Shop createTestShopA(){
        return Shop.builder()
                .id(1L)
//                .name("Masoutis")
                .build();
    }

    public static Transaction createTestTransactionA(){
        return Transaction.builder()
                .id(1L)
                .shop(createTestShopA())
//                .date("22/2/2024")
                .build();
    }
    public static Transaction createTestTransactionB(){
        return Transaction.builder()
                .id(2L)
                .shop(createTestShopA())
//                .date("26/03/2024")
                .build();
    }
    public static Transaction createTestTransactionC(){
        return Transaction.builder()
                .id(3L)
                .shop(createTestShopA())
//                .date("03/03/2024")
                .build();
    }

    public static Product createProductEntityA(){
        return Product.builder()
                .id(1L)
                .name("Milk")
                .build();
    }

    public static Product createTestProductC(){
        return Product.builder()
                .id(1L)
                .name("Cheese")
                .build();
    }
}
