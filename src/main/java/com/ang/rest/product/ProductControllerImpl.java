package com.ang.rest.product;

import com.ang.rest.domain.dto.ProductDTO;
import com.ang.rest.domain.entity.Product;
import io.quarkus.panache.common.Page;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.jboss.resteasy.reactive.RestResponse;

import java.net.URI;
import java.util.List;

@Path("api/v2/products")
public class ProductControllerImpl {
    @Inject ProductService productService;
    @Inject UriInfo uriInfo;

    @GET
    @Path("/all")
    public RestResponse<List<ProductDTO>> getAllProducts() {
        return RestResponse.ok(productService.findAll());
    }

    @GET
    public RestResponse<List<ProductDTO>> getProducts(
            @QueryParam("filter") String filter,
            @QueryParam("page") Integer page,
            @QueryParam("size") Integer size) {

        int pageIndex = page != null ? page : 0;
        int pageSize = size != null ? size : 10;
        Page panachePage = Page.of(pageIndex, pageSize);
        return RestResponse.ok(productService.findAll(filter != null ? filter : "", panachePage));
    }

    @GET
    @Path("/id/{id}")
    public RestResponse<ProductDTO> getProduct(@PathParam("id") Long id) {
        return RestResponse.ok(productService.findOne(id));
    }

    @POST
    public Response createProduct(ProductDTO productDto) {
        Product product = productService.save(productDto);
        URI location = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(product.getId()))
                .build();
        return Response.created(location).build();
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
