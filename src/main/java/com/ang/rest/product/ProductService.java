package com.ang.rest.product;

import com.ang.rest.domain.dto.ProductDTO;
import com.ang.rest.domain.entity.MeasuringType;
import com.ang.rest.domain.entity.Product;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ProductService {

    ProductDTO save(@Valid ProductDTO product);

    List<ProductDTO> findAll();

    Page<ProductDTO> findAll(String filter, Pageable pageable);

    ProductDTO findOne(Long id);

    ProductDTO update(Long id, @Valid ProductDTO dto);

    List<Product> findAllByID(List<Long> productIDList);

    void delete(Long productId);

    Product findOneEntity(Long id);

    void ensureProductNameNotExists(String name);

    MeasuringType[] getMeasuringTypes();
}
