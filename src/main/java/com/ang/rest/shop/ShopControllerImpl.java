package com.ang.rest.shop;

import com.ang.rest.domain.dto.ShopDTO;
import com.ang.rest.domain.entity.Shop;
import com.ang.rest.mapper.impl.ShopMapper;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.jboss.resteasy.reactive.RestResponse;
import io.quarkus.panache.common.Page;


import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;


@Path("api/v1/shops")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ShopControllerImpl {

    @Inject ShopService shopService;
    @Inject ShopMapper shopMapper;
    @Inject UriInfo uriInfo;
    @GET
    @Path("/id/{id}")
    public RestResponse<ShopDTO> getShop(@PathParam("id") Long id) {
        Shop shop = shopService.findOneEntity(id);
        return RestResponse.ok(shopMapper.mapToDto(shop));
    }

    @GET
    public List<ShopDTO> getShops(
            @QueryParam("filter") String filter,
            @QueryParam("page") Integer page,
            @QueryParam("size") Integer size) {
        int pageIndex = page != null ? page : 0;
        int pageSize = size != null ? size : 10;
        Page panachePage = Page.of(pageIndex, pageSize);
        return shopService.findAll(filter != null ? filter : "", panachePage);
    }

    @GET
    @Path("/all")
    public RestResponse<List<ShopDTO>> getAllShops() {
        return RestResponse.ok(shopService.findAll());
    }

    @POST
    public Response createShop(ShopDTO shopDto) {
        Shop shop = shopService.save(shopDto);
        URI location = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(shop.getId()))
                .build();
        return Response.created(location).build();
    }

    @PUT
    @Path("/id/{id}")
    public RestResponse<ShopDTO> updateShop(@PathParam("id") Long id, ShopDTO shopDto) {
        return  RestResponse.ok(shopService.update(id, shopDto));
    }

    @DELETE
    @Path("/id/{id}")
    public RestResponse<Void> deleteShop(@PathParam("id") Long id) {
        shopService.delete(id);
        return  RestResponse.noContent();
    }
}
