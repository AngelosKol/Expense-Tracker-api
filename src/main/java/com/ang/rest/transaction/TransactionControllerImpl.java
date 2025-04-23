package com.ang.rest.transaction;

import com.ang.rest.domain.dto.*;
import com.ang.rest.domain.entity.*;
import com.ang.rest.mapper.impl.TransactionMapper;
import io.quarkus.panache.common.Page;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.jboss.resteasy.reactive.RestResponse;


import java.net.URI;
import java.util.List;

@Path("api/v2/transactions")
public class TransactionControllerImpl implements TransactionController {
    @Inject TransactionService transactionService;
    @Inject TransactionMapper transactionMapper;

    @Inject UriInfo uriInfo;

    @GET
    @Path("/all")
    public List<TransactionDTO> getAllTransactions() {
        return transactionService.findAll();
    }

    @GET
    public RestResponse<List<TransactionDTO>> getTransactions(
            @QueryParam("page") Integer page,
            @QueryParam("size") Integer size) {
        int pageIndex = page != null ? page : 0;
        int pageSize = size != null ? size : 10;
        Page panachePage = Page.of(pageIndex, pageSize);
        return RestResponse.ok(transactionService.findAll(panachePage));
    }

    @GET
    @Path("/id/{id}")
    public RestResponse<TransactionDTO> getTransactionById(@PathParam("id") Long id) {
        return  RestResponse.ok(transactionService.findOne(id));
    }

    @POST
    public Response createTransaction(TransactionDTO transactionDto) {
        Transaction savedTransaction = transactionService.save(transactionDto);
        URI location = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(savedTransaction.getId()))
                .build();
        return  Response.created(location).build();

    }

    @DELETE
    @Path("/id/{id}")
    public RestResponse<Void> deleteTransaction(@PathParam("id") Long id) {
        transactionService.delete(id);
        return  RestResponse.noContent();
    }
}