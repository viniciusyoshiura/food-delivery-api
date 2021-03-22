package com.mycompany.fooddelivery.api.v1.openapi.controller;


import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.mycompany.fooddelivery.api.controller.exception.handler.Problem;
import com.mycompany.fooddelivery.api.v1.model.dto.PaymentMethodDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Restaurants")
public interface RestaurantPaymentMethodControllerOpenApi {

	@ApiOperation("Lists restaurants payment methods")
	@ApiResponses({ @ApiResponse(code = 404, message = "Restaurant not found", response = Problem.class) })
	CollectionModel<PaymentMethodDTO> list(@ApiParam(value = "Restaurant ID", example = "1", required = true) Long restaurantId);

	@ApiOperation("Disassociation of restaurant with payment method")
	@ApiResponses({ @ApiResponse(code = 204, message = "Disassociation successfully registered"),
			@ApiResponse(code = 404, message = "Restaurant or payment method not found", response = Problem.class) })
	ResponseEntity<Void> disassociate(@ApiParam(value = "Restaurant ID", example = "1", required = true) Long restaurantId,
			@ApiParam(value = "Payment method ID", example = "1", required = true) Long paymentMethodId);

	@ApiOperation("Association of restaurant with payment method")
	@ApiResponses({ @ApiResponse(code = 204, message = "Association successfully registered"),
			@ApiResponse(code = 404, message = "Restaurant or payment method not found", response = Problem.class) })
	ResponseEntity<Void> associate(@ApiParam(value = "Restaurant ID", example = "1", required = true) Long restaurantId,
			@ApiParam(value = "Payment method ID", example = "1", required = true) Long paymentMethodId);

}
