package com.ang.rest.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class YearlyCostDto {

    private String month;
    private int totalSpent;

//    public YearlyCostDto(String month, Long totalSpent){
//        this.month = month;
//        this.totalSpent = totalSpent;
//    }
}
