package com.mycompany.fooddelivery.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.mycompany.fooddelivery.api.controller.exception.handler.Problem;
import com.mycompany.fooddelivery.api.model.dto.UserDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Restaurants")
public interface RestaurantResponsibleUserControllerOpenApi {

	@ApiOperation("Lists users responsible for restaurant")
	@ApiResponses({ @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class) })
	CollectionModel<UserDTO> list(@ApiParam(value = "Restaurant ID", example = "1", required = true) Long restaurantId);
	
	@ApiOperation("Restaurant responsible disassociation")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Disassociation successfully registered"),
        @ApiResponse(code = 404, message = "Restaurant or user not found", 
            response = Problem.class)
    })
	void disassociate(@ApiParam(value = "Restaurant ID", example = "1", required = true) Long restaurantId,
			@ApiParam(value = "User ID", example = "1", required = true) Long userId);
	
	 @ApiOperation("Restaraunt responsbile association")
	    @ApiResponses({
	        @ApiResponse(code = 204, message = "Association successfully registered"),
	        @ApiResponse(code = 404, message = "Restaurant or user not found", 
	            response = Problem.class)
	    })
	void associate(@ApiParam(value = "Restaurant ID", example = "1", required = true) Long restaurantId,
			@ApiParam(value = "User ID", example = "1", required = true) Long userId);

}
