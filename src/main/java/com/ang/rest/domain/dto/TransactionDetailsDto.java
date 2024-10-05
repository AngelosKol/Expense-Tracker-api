package com.ang.rest.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDetailsDto {


    private String name;
    private int quantity;
    private BigDecimal price;

    public String getName() {
        return this.name;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof TransactionDetailsDto)) return false;
        final TransactionDetailsDto other = (TransactionDetailsDto) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        if (this.getQuantity() != other.getQuantity()) return false;
        final Object this$price = this.getPrice();
        final Object other$price = other.getPrice();
        if (this$price == null ? other$price != null : !this$price.equals(other$price)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof TransactionDetailsDto;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        result = result * PRIME + this.getQuantity();
        final Object $price = this.getPrice();
        result = result * PRIME + ($price == null ? 43 : $price.hashCode());
        return result;
    }

    public String toString() {
        return "TransactionDetailsDto(name=" + this.getName() + ", quantity=" + this.getQuantity() + ", price=" + this.getPrice() + ")";
    }
}
