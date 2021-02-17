package com.mycompany.fooddelivery.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.mycompany.fooddelivery.api.controller.exception.handler.Problem;
import com.mycompany.fooddelivery.api.model.dto.UserDTO;
import com.mycompany.fooddelivery.api.model.input.PasswordInput;
import com.mycompany.fooddelivery.api.model.input.UserInput;
import com.mycompany.fooddelivery.api.model.input.UserWithPasswordInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Users")
public interface UserControllerOpenApi {

	@ApiOperation("Lists all users")
	public CollectionModel<UserDTO> list();

	@ApiOperation("Searches a user by ID")
	@ApiResponses({ @ApiResponse(code = 400, message = "Invalid user ID", response = Problem.class),
			@ApiResponse(code = 404, message = "User not found", response = Problem.class) })
	public UserDTO search(@ApiParam(value = "User ID", example = "1", required = true) Long userId);
	
	@ApiOperation("Registers a user")
    @ApiResponses({
        @ApiResponse(code = 201, message = "User registered"),
    })
	public UserDTO insert(
			@ApiParam(name = "body", value = "JSON representation of a new User", required = true) UserWithPasswordInput userWithPasswordInput);
	
	@ApiOperation("Updates a user by ID")
    @ApiResponses({
        @ApiResponse(code = 200, message = "User updated"),
        @ApiResponse(code = 404, message = "User not found", response = Problem.class)
    })
	public UserDTO update(@ApiParam(value = "User ID", example = "1", required = true) Long userId,
			@ApiParam(name = "body", value = "JSON representation of a user with new data", required = true) UserInput userInput);
	
	@ApiOperation("Updates a user password")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Senha alterada com sucesso"),
        @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
	public void changePassword(@ApiParam(value = "User ID", example = "1", required = true) Long userId,
			@ApiParam(name = "body", value = "JSON representation of a new password", required = true) PasswordInput passwordInput);
	
	@ApiOperation("Removes a user")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Password successfully registered"),
        @ApiResponse(code = 404, message = "User not found", response = Problem.class)
    })
	public void remove(@ApiParam(value = "User ID", example = "1", required = true) Long userId);

}
