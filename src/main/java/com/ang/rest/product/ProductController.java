package com.ang.rest.product;

import com.ang.rest.domain.dto.ProductDTO;
import com.ang.rest.domain.dto.ErrorResponse;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;

public interface ProductController {
    @Operation(summary = "Create a new product")
    @POST
    @APIResponses(value = {
            @APIResponse(
                    responseCode = "201",
                    description = "Product created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class))
            ),
            @APIResponse(
                    responseCode = "400",
                    description = "Invalid request data",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            ),
            @APIResponse(
                    responseCode = "409",
                    description = "Product already exists",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    RestResponse<ProductDTO> createProduct(@RequestBody ProductDTO productDto);


    @Operation(summary = "Get all products")
    @APIResponses(value = {@APIResponse(responseCode = "200", description = "List of products", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class))})})
    @GET
    @Path("/all")
    public List<ProductDTO> getAllProducts();

    @Operation(summary = "Get products with pagination")
    @APIResponses(value = {@APIResponse(responseCode = "200", description = "Paginated list of products", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))})})
    @GET
    public Page<ProductDTO> getProducts(Pageable pageable);

    @Operation(summary = "Get a product by ID")
    @APIResponses(value = {@APIResponse(responseCode = "200", description = "Product found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class))}), @APIResponse(responseCode = "404", description = "Product not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))})
    @GET
    @Path("/id/{id}")  
    public RestResponse<ProductDTO> getProduct(@PathParam("id") Long id);

    @Operation(summary = "Update a product by ID")
    @APIResponses(value = {@APIResponse(responseCode = "200", description = "Product updated", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class))}), @APIResponse(responseCode = "404", description = "Product not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))})
    @PUT
    @Path("/id/{id}")    
    public RestResponse<ProductDTO> updateProduct(@PathParam("id") Long id, @RequestBody ProductDTO productDto);

    @Operation(summary = "Delete a product by ID")
    @APIResponses(value = {@APIResponse(responseCode = "204", description = "Product deleted", content = @Content), @APIResponse(responseCode = "404", description = "Product not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))})
    @DELETE
    @Path("/id/{id}")       
    public RestResponse<Void> deleteProduct(@PathParam("id") Long id);
}
