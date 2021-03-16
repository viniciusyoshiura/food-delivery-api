package com.mycompany.fooddelivery.api.openapi.controller;


import javax.validation.Valid;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.ServletWebRequest;

import com.mycompany.fooddelivery.api.controller.exception.handler.Problem;
import com.mycompany.fooddelivery.api.model.dto.PaymentMethodDTO;
import com.mycompany.fooddelivery.api.model.input.PaymentMethodInput;
import com.mycompany.fooddelivery.api.openapi.model.PaymentMethodsDTOOpenApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Payment methods")
public interface PaymentMethodControllerOpenApi {

	@ApiOperation(value = "Lists all payment methods", response = PaymentMethodsDTOOpenApi.class)
	ResponseEntity<CollectionModel<PaymentMethodDTO>> list(ServletWebRequest request);

	@ApiOperation("Searchs a payment method by ID")
	@ApiResponses({ @ApiResponse(code = 400, message = "Invalid payment method ID", response = Problem.class),
			@ApiResponse(code = 404, message = "Payment method not found", response = Problem.class) })
	ResponseEntity<PaymentMethodDTO> search(
			@ApiParam(value = "Payment method ID", example = "1", required = true) @PathVariable Long paymentMethodId,
			ServletWebRequest request);

	@ApiOperation("Registers a payment method")
	@ApiResponses({ @ApiResponse(code = 201, message = "Payment method registered"), })
	PaymentMethodDTO insert(
			@ApiParam(name = "body", value = "JSON representation of a new payment method", required = true) @RequestBody @Valid PaymentMethodInput paymentMethodInput);

	@ApiOperation("Updates a city by ID")
	@ApiResponses({ @ApiResponse(code = 200, message = "Payment method updated"),
			@ApiResponse(code = 404, message = "Payment method not found", response = Problem.class) })
	PaymentMethodDTO update(
			@ApiParam(value = "Payment metho ID", example = "1", required = true) @PathVariable Long paymentMethodId,
			@ApiParam(name = "body", value = "JSON representaton of a payment metho with new data", required = true) @RequestBody @Valid PaymentMethodInput paymentMethodInput);

	@ApiOperation("Removes a payment method by ID")
	@ApiResponses({ @ApiResponse(code = 204, message = "Payment method removed"),
			@ApiResponse(code = 404, message = "Payment method not found", response = Problem.class) })
	void remove(
			@ApiParam(value = "Payment method ID", example = "1", required = true) @PathVariable Long paymentMethodId);

}
