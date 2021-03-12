package com.mycompany.fooddelivery.api.openapi.controller;

import java.util.List;

import org.springframework.hateoas.CollectionModel;

import com.mycompany.fooddelivery.api.controller.exception.handler.Problem;
import com.mycompany.fooddelivery.api.model.dto.GroupingeDTO;
import com.mycompany.fooddelivery.api.model.input.GroupingeInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Groups")
public interface GroupingeControllerOpenApi {

	@ApiOperation("List of all groups")
	CollectionModel<GroupingeDTO> list();

	@ApiOperation("Search a group by ID")
	@ApiResponses({ @ApiResponse(code = 400, message = "Invalid group ID", response = Problem.class),
			@ApiResponse(code = 404, message = "Group not found", response = Problem.class) })
	GroupingeDTO search(@ApiParam(value = "Group ID", example = "1", required = true) Long groupingeId);

	@ApiOperation("Registers a group")
	@ApiResponses({ @ApiResponse(code = 201, message = "Group registered"), })
	GroupingeDTO insert(
			@ApiParam(name = "body", value = "JSON representation of a new group", required = true) GroupingeInput groupingeInput);

	@ApiOperation("Updates a group by ID")
	@ApiResponses({ @ApiResponse(code = 200, message = "Group updated"),
			@ApiResponse(code = 404, message = "Group not found", response = Problem.class) })
	GroupingeDTO update(@ApiParam(value = "Group ID", example = "1", required = true) Long groupingeId,
			@ApiParam(name = "corpo", value = "JSON group representation", required = true) GroupingeInput groupingeInput);

	@ApiOperation("Removes a group by ID")
	@ApiResponses({ @ApiResponse(code = 204, message = "Group removed"),
			@ApiResponse(code = 404, message = "Group not found", response = Problem.class) })
	void remove(@ApiParam(value = "Group ID", example = "1", required = true) Long groupingeId);

}
