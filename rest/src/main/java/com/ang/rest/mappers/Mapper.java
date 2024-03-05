package com.ang.rest.mappers;

import com.ang.rest.domain.dto.TransactionDto;
import com.ang.rest.domain.entities.TransactionEntity;

public interface Mapper <A, B>{
    B mapTo(A a);


    A mapFrom(B b);
}
