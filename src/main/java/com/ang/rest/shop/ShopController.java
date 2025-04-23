package com.ang.rest.shop;

import com.ang.rest.domain.dto.ShopDTO;
import com.ang.rest.domain.entity.Shop;
import com.ang.rest.domain.dto.ErrorResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;

@Path("api/v2/shops")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface ShopController {

    @GET
    @Path("/id/{id}")
    @Operation(summary = "Get a shop by ID")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Found the shop", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Shop.class))),
            @APIResponse(responseCode = "404", description = "Shop not found", content = @Content(mediaType = "application/json"))
    })
    RestResponse<ShopDTO> getShop(@PathParam("id") Long id);

    @GET
    @Operation(summary = "Get all shops with pagination")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Found the shops", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ShopDTO.class)))
    })
    RestResponse<Shop> getShops(@QueryParam("page") int page, @QueryParam("size") int size);

    @GET
    @Path("/all")
    @Operation(summary = "Get all shops")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Found the shops", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ShopDTO.class)))
    })
    RestResponse<List<ShopDTO>> getAllShops();

    @POST
    @Operation(summary = "Create a new shop")
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "Shop created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ShopDTO.class)))
    })
    RestResponse<ShopDTO> createShop(ShopDTO shopDto);

    @PUT
    @Path("/id/{id}")
    @Operation(summary = "Update a shop by ID")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Shop updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ShopDTO.class))),
            @APIResponse(responseCode = "404", description = "Shop not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @APIResponse(responseCode = "409", description = "Shop already exists", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    RestResponse<ShopDTO> updateShop(@PathParam("id") Long id, ShopDTO shopDto);

    @DELETE
    @Path("/id/{id}")
    @Operation(summary = "Delete a shop by ID")
    @APIResponses(value = {
            @APIResponse(responseCode = "204", description = "Shop deleted", content = @Content),
            @APIResponse(responseCode = "404", description = "Shop not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    RestResponse<Void> deleteShop(@PathParam("id") Long id);
}