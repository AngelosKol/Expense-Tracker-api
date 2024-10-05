package com.ang.rest.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    public Long id;
    public String name;

    public static ProductDtoBuilder builder() {
        return new ProductDtoBuilder();
    }

    public static class ProductDtoBuilder {
        private Long id;
        private String name;

        ProductDtoBuilder() {
        }

        public ProductDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ProductDtoBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ProductDto build() {
            return new ProductDto(this.id, this.name);
        }

        public String toString() {
            return "ProductDto.ProductDtoBuilder(id=" + this.id + ", name=" + this.name + ")";
        }
    }
}
