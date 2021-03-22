package com.mycompany.fooddelivery.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.mycompany.fooddelivery.api.controller.exception.handler.Problem;
import com.mycompany.fooddelivery.api.v1.model.dto.ProductDTO;
import com.mycompany.fooddelivery.api.v1.model.input.ProductInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Products")
public interface RestaurantProductControllerOpenApi {

	@ApiOperation("Products list of a restaurant")
	@ApiResponses({ @ApiResponse(code = 400, message = "Invalid restaurant ID", response = Problem.class),
			@ApiResponse(code = 404, message = "Restaurant not found", response = Problem.class) })
	CollectionModel<ProductDTO> list(@ApiParam(value = "Restaurant ID", example = "1", required = true) Long restaurantId,
			@ApiParam(value = "Indicates whether or not to include inactive products in the listing result", example = "false", defaultValue = "false") Boolean includeInactives);

	@ApiOperation("Searchs a product of a restaurant")
	@ApiResponses({ @ApiResponse(code = 400, message = "Invalid Resturant or product ID", response = Problem.class),
			@ApiResponse(code = 404, message = "Product of restaurant not found", response = Problem.class) })
	ProductDTO search(@ApiParam(value = "Restaurant ID", example = "1", required = true) Long restaurantId,
			@ApiParam(value = "Product ID", example = "1", required = true) Long productId);

	@ApiOperation("Registers a product of a restaurant")
	@ApiResponses({ @ApiResponse(code = 201, message = "Product registered"),
			@ApiResponse(code = 404, message = "Restaurant not found", response = Problem.class) })
	ProductDTO insert(@ApiParam(value = "Restaurant ID", example = "1", required = true) Long restaurantId,
			@ApiParam(name = "body", value = "JSON representation of a new product", required = true) ProductInput productInput);

	@ApiOperation("Updates a product of a restaurant")
	@ApiResponses({ @ApiResponse(code = 200, message = "Product updated"),
			@ApiResponse(code = 404, message = "Product of restaurant not found", response = Problem.class) })
	ProductDTO update(@ApiParam(value = "Restaurant ID", example = "1", required = true) Long restaurantId,
			@ApiParam(value = "Product ID", example = "1", required = true) Long productId,
			@ApiParam(name = "body", value = "JSON representation of a product with new data", required = true) ProductInput productInput);

}
