package com.mycompany.fooddelivery.api.openapi.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.mycompany.fooddelivery.api.controller.exception.handler.Problem;
import com.mycompany.fooddelivery.api.model.dto.RestaurantBasicDTO;
import com.mycompany.fooddelivery.api.model.dto.RestaurantDTO;
import com.mycompany.fooddelivery.api.model.dto.RestaurantOnlyNameDTO;
import com.mycompany.fooddelivery.api.model.input.RestaurantInput;
import com.mycompany.fooddelivery.api.openapi.model.RestaurantSummaryDTOOpenApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "Restaurants")
public interface RestaurantControllerOpenApi {

	@ApiOperation(value = "List of restaurants", response = RestaurantSummaryDTOOpenApi.class)
	CollectionModel<RestaurantBasicDTO> list();
	
	@ApiIgnore
	@ApiOperation(value = "Lista of restaurants", hidden = true)
	CollectionModel<RestaurantOnlyNameDTO> listOnlyNames(); 
	
	@ApiOperation("Searchs a restaurant by ID")
	@ApiResponses({ @ApiResponse(code = 400, message = "Invalid restaurant ID", response = Problem.class),
			@ApiResponse(code = 404, message = "Restaurant not found", response = Problem.class) })
	RestaurantDTO search(
			@ApiParam(value = "Restaurant ID", example = "1", required = true) @PathVariable Long restaurantId);

	@ApiOperation("Registers a restaurant")
	@ApiResponses({ @ApiResponse(code = 201, message = "Restaurant registered"), })
	RestaurantDTO insert(
			@ApiParam(name = "body", value = "JSON representation of a new restaurant", required = true) @RequestBody @Valid RestaurantInput restaurantInput);

	@ApiOperation("Updates a restaurant by ID")
	@ApiResponses({ @ApiResponse(code = 200, message = "Restaurant updated"),
			@ApiResponse(code = 404, message = "Restaurant not found", response = Problem.class) })
	RestaurantDTO update(
			@ApiParam(value = "Restaurant ID", example = "1", required = true) @PathVariable Long restaurantId,
			@ApiParam(name = "body", value = "JSON representation of a restaurant with new data", required = true) @RequestBody @Valid RestaurantInput restaurantInput);

	@ApiOperation("Updates a restaurant by ID")
	@ApiResponses({ @ApiResponse(code = 200, message = "Restaurant updated"),
			@ApiResponse(code = 404, message = "Restaurant not found", response = Problem.class) })
	RestaurantDTO partialUpdate(
			@ApiParam(value = "Restaurant ID", example = "1", required = true) @PathVariable Long restaurantId,
			@ApiParam(name = "body", value = "JSON representation of a restaurant with new data", required = true) @RequestBody Map<String, Object> fields,
			HttpServletRequest request);

	@ApiOperation("Activates a restaurant by ID")
	@ApiResponses({ @ApiResponse(code = 204, message = "Restaurant successfully activated"),
			@ApiResponse(code = 404, message = "Restaurant not found", response = Problem.class) })
	ResponseEntity<Void> activate(@ApiParam(value = "Restaurant ID", example = "1", required = true) @PathVariable Long restaurantId);

	@ApiOperation("Deactivates a restaurant by ID")
	@ApiResponses({ @ApiResponse(code = 204, message = "Restaurant successfully deactivated"),
			@ApiResponse(code = 404, message = "Restaurant not found", response = Problem.class) })
	ResponseEntity<Void> deactivate(@ApiParam(value = "Restaurant ID", example = "1", required = true) @PathVariable Long restaurantId);

	@ApiOperation("Activates multiples restaurants")
	@ApiResponses({ @ApiResponse(code = 204, message = "Restaurants successfully activated") })
	void activateMultiple(
			@ApiParam(name = "body", value = "Restaurants IDs", required = true) @RequestBody List<Long> restaurantIds);

	@ApiOperation("Deactivates multiples restaurants")
	@ApiResponses({ @ApiResponse(code = 204, message = "Restaurants successfully deactivated") })
	void deactivateMultiple(
			@ApiParam(name = "body", value = "Restaurants IDs", required = true) @RequestBody List<Long> restaurantIds);

	@ApiOperation("Opens a restaurant by ID")
	@ApiResponses({ @ApiResponse(code = 204, message = "Restaurant successfully opened"),
			@ApiResponse(code = 404, message = "Restaurant not found", response = Problem.class) })
	ResponseEntity<Void> open(@ApiParam(value = "Restaurant ID", example = "1", required = true) @PathVariable Long restaurantId);

	@ApiOperation("Closes a restaurant by ID")
	@ApiResponses({ @ApiResponse(code = 204, message = "Restaurant successfully closed"),
			@ApiResponse(code = 404, message = "Restaurant not found", response = Problem.class) })
	ResponseEntity<Void> close(@ApiParam(value = "Restaurant ID", example = "1", required = true) @PathVariable Long restaurantId);

}
