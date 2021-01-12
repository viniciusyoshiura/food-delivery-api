package com.mycompany.fooddelivery.api.openapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.mycompany.fooddelivery.api.controller.exception.handler.Problem;
import com.mycompany.fooddelivery.api.model.dto.StateDTO;
import com.mycompany.fooddelivery.api.model.input.StateInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "States")
public interface StateControllerOpenApi {

	@ApiOperation("Lists all states")
	List<StateDTO> list();

	@ApiOperation("Searchs a state by ID")
	@ApiResponses({ @ApiResponse(code = 400, message = "Invalid state ID", response = Problem.class),
			@ApiResponse(code = 404, message = "State not found", response = Problem.class) })
	StateDTO search(@ApiParam(value = "State ID", example = "1", required = true) @PathVariable Long stateId);

	@ApiOperation("Registers a state")
	@ApiResponses({ @ApiResponse(code = 201, message = "State registered"), })
	StateDTO insert(
			@ApiParam(name = "body", value = "JSON representantion of a new state", required = true) @RequestBody @Valid StateInput stateInput);
	
	@ApiOperation("Updates a state by ID")
    @ApiResponses({
        @ApiResponse(code = 200, message = "State updated"),
        @ApiResponse(code = 404, message = "State not found", response = Problem.class)
    })
	StateDTO update(@ApiParam(value = "State ID", example = "1", required = true) @PathVariable Long stateId,
			@RequestBody @Valid StateInput stateInput);
	
	@ApiOperation("Removes a state by ID")
    @ApiResponses({
        @ApiResponse(code = 204, message = "State removed"),
        @ApiResponse(code = 404, message = "State not found", response = Problem.class)
    })
	void remove(@ApiParam(value = "State ID", example = "1", required = true) @PathVariable Long stateId);

}
