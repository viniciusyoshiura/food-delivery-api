package com.mycompany.fooddelivery.api.v1.openapi.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.mycompany.fooddelivery.api.controller.exception.handler.Problem;
import com.mycompany.fooddelivery.api.v1.model.dto.KitchenDTO;
import com.mycompany.fooddelivery.api.v1.model.input.KitchenInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Kitchens")
public interface KitchenControllerOpenApi {

	@ApiOperation("Kitchens list with pagination")
	PagedModel<KitchenDTO> list(@PageableDefault(size = 10) Pageable pageable);

	@ApiOperation("Searchs a kitchen by ID")
	@ApiResponses({ @ApiResponse(code = 400, message = "Invalid kitchen ID", response = Problem.class),
			@ApiResponse(code = 404, message = "Kitchen not found", response = Problem.class) })
	KitchenDTO search(@ApiParam(value = "Kitchen ID", example = "1", required = true) @PathVariable Long kitchenId);

	@ApiOperation("Registers a kitchen")
	@ApiResponses({ @ApiResponse(code = 201, message = "Kitchen registered"), })
	KitchenDTO insert(
			@ApiParam(name = "body", value = "JSON Representation of a new kitchen", required = true) @RequestBody @Valid KitchenInput kitchenInput);

	@ApiOperation("Updates a kitchen by ID")
	@ApiResponses({ @ApiResponse(code = 200, message = "Kitchen updated"),
			@ApiResponse(code = 404, message = "Kitchen not found", response = Problem.class) })
	KitchenDTO update(@ApiParam(value = "Kitchen ID", example = "1", required = true) @PathVariable Long kitchenId,
			@ApiParam(name = "body", value = "JSON representation of a kitchen with new data") @RequestBody @Valid KitchenInput kitchenInput);

	@ApiOperation("Removes a kitchen by ID")
	@ApiResponses({ @ApiResponse(code = 204, message = "Kitchen removed"),
			@ApiResponse(code = 404, message = "Kitchen not found", response = Problem.class) })
	void remove(@ApiParam(value = "Kitchen ID", example = "1", required = true) @PathVariable Long kitchenId);

}
