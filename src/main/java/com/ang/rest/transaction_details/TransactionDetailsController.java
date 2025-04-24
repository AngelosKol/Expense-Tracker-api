package com.ang.rest.transaction_details;

import com.ang.rest.domain.dto.TransactionDetailsDTO;
import io.quarkus.panache.common.Page;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;

public interface TransactionDetailsController {
    @Operation(summary = "Get all transaction details", description = "Retrieve all details for a specific transaction")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Successfully retrieved transaction details", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionDetailsDTO.class))),
            @APIResponse(responseCode = "404", description = "Transaction not found")
    })
    @GET
            @Path("/id/{id}/details/all")
    RestResponse<List<TransactionDetailsDTO>> getAllTransactionDetails(@PathParam("id") Long id);

    @Operation(summary = "Get paginated transaction details", description = "Retrieve paginated details for a specific transaction")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Successfully retrieved paginated transaction details", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionDetailsDTO.class))),
            @APIResponse(responseCode = "404", description = "Transaction not found")
    })
    @GET
    @Path("/id/{id}/details")
    List<TransactionDetailsDTO> getTransactionDetailsByTransactionId(@PathParam("id") Long id, Page page);

    @Operation(summary = "Delete product from transaction", description = "Delete a product from an existing transaction by product name")
    @APIResponses(value = {
            @APIResponse(responseCode = "204", description = "Product removed from transaction successfully"),
            @APIResponse(responseCode = "404", description = "Transaction or product not found")
    })
    @DELETE
    @Path("/id/{id}/product/{productName}")
    RestResponse<Void> deleteProductFromTransaction(@PathParam("id") Long transactionId, @PathParam("productName") String productName);

}
