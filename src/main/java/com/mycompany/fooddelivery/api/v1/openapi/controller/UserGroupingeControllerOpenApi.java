package com.mycompany.fooddelivery.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.mycompany.fooddelivery.api.controller.exception.handler.Problem;
import com.mycompany.fooddelivery.api.v1.model.dto.GroupingeDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Users")
public interface UserGroupingeControllerOpenApi {

	@ApiOperation("Lists all users associated with groups")
	@ApiResponses({ @ApiResponse(code = 404, message = "User not found", response = Problem.class) })
	public CollectionModel<GroupingeDTO> list(@ApiParam(value = "User ID", example = "1", required = true) Long userId);

	@ApiOperation("Disassociation of group with user")
	@ApiResponses({ @ApiResponse(code = 204, message = "Disassociation successfully registered"),
			@ApiResponse(code = 404, message = "User or group not found", response = Problem.class) })
	public ResponseEntity<Void> disassociate(@ApiParam(value = "User ID", example = "1", required = true) Long userId,
			@ApiParam(value = "Group ID", example = "1", required = true) Long groupId);

	@ApiOperation("Association of group with user")
	@ApiResponses({ @ApiResponse(code = 204, message = "Association successfully registered"),
			@ApiResponse(code = 404, message = "User or group not found", response = Problem.class) })
	public ResponseEntity<Void> associate(@ApiParam(value = "User ID", example = "1", required = true) Long userId,
			@ApiParam(value = "Group ID", example = "1", required = true) Long groupId);

}
