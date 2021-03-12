package com.mycompany.fooddelivery.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.mycompany.fooddelivery.api.model.dto.PermissionDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Permissions")
public interface PermissionControllerOpenApi {

	@ApiOperation("Permissions list")
    CollectionModel<PermissionDTO> list();
	
}
