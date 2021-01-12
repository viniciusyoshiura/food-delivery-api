package com.mycompany.fooddelivery.api.openapi.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.multipart.MultipartFile;

import com.mycompany.fooddelivery.api.controller.exception.handler.Problem;
import com.mycompany.fooddelivery.api.model.dto.ProductPhotoDTO;
import com.mycompany.fooddelivery.api.model.input.ProductPhotoInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Products")
public interface RestaurantProductPhotoControllerOpenApi {

	@ApiOperation("Updates the product photo of a restaurant")
	@ApiResponses({ @ApiResponse(code = 200, message = "Product photo updated"),
			@ApiResponse(code = 404, message = "Product of restaruant not found", response = Problem.class) })
	public ProductPhotoDTO updatePhoto(
			@ApiParam(value = "Restaurant ID", example = "1", required = true) Long restaurantId,
			@ApiParam(value = "Product ID", example = "1", required = true) Long productId,
			ProductPhotoInput productPhotoInput, @ApiParam(value = "Product photo file (maximum 500KB, JPG and PNG only)",
					required = true) MultipartFile file) throws IOException;

	@ApiOperation(value = "Gets the restaurant product photo", produces = "application/json, image/jpeg, image/png")
	@ApiResponses({ @ApiResponse(code = 400, message = "Invalid restaurant or product ID", response = Problem.class),
			@ApiResponse(code = 404, message = "Product photo not found", response = Problem.class) })
	public ProductPhotoDTO search(@ApiParam(value = "Restaurant ID", example = "1", required = true) Long restaurantId,
			@ApiParam(value = "Product ID", example = "1", required = true) Long productId);

	@ApiOperation(value = "Gets the restaurant product photo", hidden = true)
	public ResponseEntity<?> getPhoto(
			@ApiParam(value = "Restaurant ID", example = "1", required = true) Long restaurantId,
			@ApiParam(value = "Product ID", example = "1", required = true) Long productId,
			@RequestHeader(name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException;

	@ApiOperation("Removes a restaurant product photo")
	@ApiResponses({ @ApiResponse(code = 204, message = "Product photo successfully removed"),
			@ApiResponse(code = 400, message = "Invalid restaurant or product ID", response = Problem.class),
			@ApiResponse(code = 404, message = "Product photo not found", response = Problem.class) })
	public void remove(@ApiParam(value = "Restaurant ID", example = "1", required = true) Long restaurantId,
			@ApiParam(value = "Product ID", example = "1", required = true) Long productId);

}
