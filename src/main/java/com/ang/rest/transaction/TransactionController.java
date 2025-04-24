package com.ang.rest.transaction;

import com.ang.rest.domain.dto.TransactionDTO;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;

public interface TransactionController {

    @Operation(summary = "Create a transaction", description = "Create a new transaction with associated shop")
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "Transaction created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionDTO.class))),
            @APIResponse(responseCode = "404", description = "Shop not found", content = @Content(mediaType = "application/json"))
    })
    @POST
    Response createTransaction(@RequestBody TransactionDTO transactionDto);

    @Operation(summary = "Get all transactions", description = "Retrieve all transactions")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Successfully retrieved transactions", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionDTO.class)))
    })
    @GET
    @Path("/all")
    List<TransactionDTO> getAllTransactions();

    @Operation(summary = "Get transactions by page", description = "Retrieve transactions in a paginated format")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Successfully retrieved paginated transactions", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionDTO.class)))
    })
    @GET
    RestResponse<List<TransactionDTO>> getTransactions(Integer page, Integer pageSize);

    @Operation(summary = "Get a specific transaction", description = "Retrieve a transaction by its ID")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Successfully retrieved the transaction", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionDTO.class))),
            @APIResponse(responseCode = "404", description = "Transaction not found")
    })
    @GET
    @Path("/id/{id}")
    RestResponse<TransactionDTO> getTransactionById(@PathParam("id") Long id);

    @Operation(summary = "Delete a transaction", description = "Delete a transaction by its ID")
    @APIResponses(value = {
            @APIResponse(responseCode = "204", description = "Transaction deleted successfully"),
            @APIResponse(responseCode = "404", description = "Transaction not found")
    })
    @DELETE
    @Path("/id/{id}")
    RestResponse<Void> deleteTransaction(@PathParam("id") Long id);

}
