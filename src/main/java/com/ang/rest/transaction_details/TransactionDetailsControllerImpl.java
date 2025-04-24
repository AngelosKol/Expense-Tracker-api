package com.ang.rest.transaction_details;

import com.ang.rest.domain.dto.ProductDetailsDTO;
import com.ang.rest.domain.dto.TransactionDetailsDTO;
import com.ang.rest.transaction.TransactionService;
import io.quarkus.panache.common.Page;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;

@RequiredArgsConstructor
@Path("api/v2/transactions")
public class TransactionDetailsControllerImpl {
    @Inject
    TransactionDetailsService transactionDetailsService;
    @Inject
    TransactionService transactionService;

    @POST
    @Path("/id/{id}/product")
    public RestResponse<Void> addProductToTransaction(@PathParam("id") Long id,  ProductDetailsDTO productDetailsDto) {
        transactionDetailsService.addProductToTransaction(id, productDetailsDto);
        return  RestResponse.ok();
    }

    @POST
    @Path("/id/{id}/products")
    public RestResponse<Void> addProductsBatch(@PathParam("id") Long id,  List<ProductDetailsDTO> productDetailsDto) {
        transactionDetailsService.addProductsBatch(id, productDetailsDto);
        return  RestResponse.ok();
    }

    @GET
    @Path("/id/{id}/details/all")
    public RestResponse<List<TransactionDetailsDTO>> getAllTransactionDetails(@PathParam("id") Long id) {
        return RestResponse.ok(transactionDetailsService.findAllTransactionDetailsByTransactionId(id));
    }

    @GET
    @Path("/id/{id}/details")
    public RestResponse<List<TransactionDetailsDTO>> getTransactionDetailsByTransactionId(
            @PathParam("id") Long id,
            @QueryParam("page") Integer page,
            @QueryParam("size") Integer size) {
        int pageIndex = page != null ? page : 0;
        int pageSize = size != null ? size : 10;
        Page panachePage = Page.of(pageIndex, pageSize);
        return RestResponse.ok(transactionDetailsService.findTransactionDetailsByTransactionId(id, panachePage));
    }

    @DELETE
    @Path("/id/{id}/product/{productName}")
    public RestResponse<Void> deleteProductFromTransaction(@PathParam("id") Long transactionId, @PathParam("productName") String productName) {
        if (!transactionService.existsById(transactionId)) {
            return  RestResponse.notFound();
        }
        transactionDetailsService.deleteProduct(transactionId, productName);
        return  RestResponse.noContent();
    }
}
