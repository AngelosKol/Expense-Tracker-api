package com.ang.rest.product;

import com.ang.rest.domain.dto.ProductDTO;
import io.quarkus.panache.common.Page;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;

@Path("api/v2/products")
public class ProductControllerImpl {

    @Inject
    ProductService productService;


    @POST
    public RestResponse<ProductDTO> createProduct(ProductDTO productDto) {
        ProductDTO created = productService.save(productDto);
        return RestResponse.status(Response.Status.CREATED, created);
    }


    @GET
    @Path("/all")
    public RestResponse<List<ProductDTO>> getAllProducts() {
        return RestResponse.ok(productService.findAll());
    }


    @GET
    public RestResponse<List<ProductDTO>> getProducts(
            @QueryParam("filter") @DefaultValue("") String filter,
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("10") int size) {
        Page panachePage = Page.of(page, size);
        return RestResponse.ok(productService.findAll(filter, panachePage));
    }


    @GET
    @Path("/id/{id}")
    public RestResponse<ProductDTO> getProduct(@PathParam("id") Long id) {
        return RestResponse.ok(productService.findOne(id));
    }

    @PUT
    @Path("/id/{id}")
    public RestResponse<ProductDTO> updateProduct(@PathParam("id") Long id, ProductDTO productDto) {
        return RestResponse.ok(productService.update(id, productDto));
    }


    @DELETE
    @Path("/id/{id}")
    public RestResponse<Void> deleteProduct(@PathParam("id") Long id) {
        productService.delete(id);
        return RestResponse.noContent();
    }
}
