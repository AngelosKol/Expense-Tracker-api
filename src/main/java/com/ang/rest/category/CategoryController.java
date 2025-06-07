package com.ang.rest.category;

import com.ang.rest.domain.dto.CategoryDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;


@Path("api/v1/categories")
public class CategoryController {

   @Inject CategoryServiceImpl categoryService;
    @GET
    public RestResponse<List<CategoryDTO>> getCategories() {
        return RestResponse.ok(categoryService.findAll());
    }
}
