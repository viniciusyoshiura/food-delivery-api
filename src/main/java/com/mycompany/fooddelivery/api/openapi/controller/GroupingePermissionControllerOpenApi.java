package com.mycompany.fooddelivery.api.openapi.controller;

import java.util.List;

import com.mycompany.fooddelivery.api.controller.exception.handler.Problem;
import com.mycompany.fooddelivery.api.model.dto.PermissionDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Groups")
public interface GroupingePermissionControllerOpenApi {

	@ApiOperation("List of permissions associated with a group")
	@ApiResponses({ @ApiResponse(code = 400, message = "Invalid group ID", response = Problem.class),
			@ApiResponse(code = 404, message = "Group not found", response = Problem.class) })
	public List<PermissionDTO> list(@ApiParam(value = "Group ID", example = "1", required = true) Long groupId);
	
	@ApiOperation("Disassociation of group permission")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Disassocitation successfully registered"),
        @ApiResponse(code = 404, message = "Group or permission not found", 
            response = Problem.class)
    })
	public void disassociate(@ApiParam(value = "Group ID", example = "1", required = true) Long groupId,
			@ApiParam(value = "Permission ID", example = "1", required = true) Long permissionId);
	
	@ApiOperation("Association of group permission")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Associtation successfully registered"),
        @ApiResponse(code = 404, message = "Group or permission not found", 
            response = Problem.class)
    })
	public void associate(@ApiParam(value = "Group ID", example = "1", required = true) Long groupId,
			@ApiParam(value = "Permission ID", example = "1", required = true) Long permissionId);

}