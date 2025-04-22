package com.ang.rest.product;

import com.ang.rest.domain.dto.ProductDTO;
import com.ang.rest.domain.entity.Product;
import io.quarkus.panache.common.Page;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ProductService {

    Product save(ProductDTO product);

    List<ProductDTO> findAll();

    List<ProductDTO> findAll(String filter, Page page);

    ProductDTO findOne(Long id);

    ProductDTO update(Long id, ProductDTO dto);

    List<Product> findAllByID(List<Long> productIDList);

    void delete(Long productId);

    Product findOneEntity(Long id);

}
