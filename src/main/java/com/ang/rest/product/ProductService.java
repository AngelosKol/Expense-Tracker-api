package com.ang.rest.product;

import com.ang.rest.domain.dto.ProductDto;
import com.ang.rest.domain.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ProductService {

    ProductDto save(ProductDto product);

    List<ProductDto> findAll();

    Page<ProductDto> findAll(String filter, Pageable pageable);

    ProductDto findOne(Long id);

    void validateExists(Long id);

    List<Product> findAllByID(List<Long> productIDList);

    void delete(Long productId);

    Product findOneEntity(Long id);

    void ensureProductNameNotExists(String name);
}
