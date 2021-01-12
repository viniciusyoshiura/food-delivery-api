package com.mycompany.fooddelivery.api.openapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.mycompany.fooddelivery.api.controller.exception.handler.Problem;
import com.mycompany.fooddelivery.api.model.dto.CityDTO;
import com.mycompany.fooddelivery.api.model.input.CityInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

//---------- @Api associates with created Tag in SpringFoxConfig.apiDocket
@Api(tags = "Cities")
public interface CityControllerOpenApi {

	// ---------- @ApiOperation - describes the endpoint in Swagger
	@ApiOperation("List all cities")
	@GetMapping
	List<CityDTO> list();

	// ---------- @ApiOperation - describes the endpoint in Swagger
	// ---------- @ApiParam - describes the parameter in Swagger
	// ---------- @ApiResponses - sets the response in Swagger
	@ApiOperation("Search a city by ID")
	@ApiResponses({ @ApiResponse(code = 400, message = "Invalid city ID", response = Problem.class),
			@ApiResponse(code = 404, message = "City not found", response = Problem.class) })
	@GetMapping("/{cityId}")
	CityDTO search(@ApiParam(value = "City ID", example = "1", required = true) @PathVariable Long cityId);

	// ---------- @ApiOperation - describes the endpoint in Swagger
	// ---------- @ApiParam - describes the parameter in Swagger
	// ---------- @ApiResponses - sets the response in Swagger
	@ApiOperation("Register a new city")
	@ApiResponses({ @ApiResponse(code = 201, message = "City registered"), })
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	CityDTO insert(
			@ApiParam(name = "body", value = "JSON representation of a new city") @RequestBody @Valid CityInput cityInput);

	// ---------- @ApiOperation - describes the endpoint in Swagger
	// ---------- @@ApiParam - describes the parameter in Swagger
	// ---------- @ApiResponses - sets the response in Swagger
	@ApiOperation("Updates a city by ID")
	@ApiResponses({ @ApiResponse(code = 200, message = "City updated"),
			@ApiResponse(code = 404, message = "City not found", response = Problem.class) })
	@PutMapping("/{cityId}")
	CityDTO update(@ApiParam(value = "City ID", example = "1", required = true) @PathVariable Long cityId,
			@ApiParam(name = "body", value = "JSON representation of a city with new data") @RequestBody @Valid CityInput cityInput);

	// ---------- @ApiOperation - describes the endpoint in Swagger
	// ---------- @@ApiParam - describes the parameter in Swagger
	// ---------- @ApiResponses - sets the response in Swagger
	@ApiOperation("Removes a city by ID")
	@ApiResponses({ @ApiResponse(code = 204, message = "City removed"),
			@ApiResponse(code = 404, message = "City not found", response = Problem.class) })
	@DeleteMapping("/{cityId}")
	void remove(@ApiParam(value = "City ID", example = "1", required = true) @PathVariable Long cityId);

}
