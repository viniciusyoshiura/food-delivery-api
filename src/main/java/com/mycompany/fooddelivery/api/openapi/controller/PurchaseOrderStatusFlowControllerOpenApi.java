package com.mycompany.fooddelivery.api.openapi.controller;

import org.springframework.http.ResponseEntity;

import com.mycompany.fooddelivery.api.controller.exception.handler.Problem;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Orders")
public interface PurchaseOrderStatusFlowControllerOpenApi {

	@ApiOperation("Order confirmation")
	@ApiResponses({ @ApiResponse(code = 204, message = "Order confirmation successfully registered"),
			@ApiResponse(code = 404, message = "Order not found", response = Problem.class) })
	ResponseEntity<Void> confirm(
			@ApiParam(value = "Order UUID", example = "f9981ca4-5a5e-4da3-af04-933861df3e55", required = true) String purchaseOrderUuid);

	@ApiOperation("Order cancelation")
	@ApiResponses({ @ApiResponse(code = 204, message = "Order cancelation successfully registered"),
			@ApiResponse(code = 404, message = "Order not found", response = Problem.class) })
	ResponseEntity<Void> cancel(
			@ApiParam(value = "Order UUID", example = "f9981ca4-5a5e-4da3-af04-933861df3e55", required = true) String purchaseOrderUuid);

	@ApiOperation("Order delivery")
	@ApiResponses({ @ApiResponse(code = 204, message = "Order delivery successfully registered"),
			@ApiResponse(code = 404, message = "Order not found", response = Problem.class) })
	ResponseEntity<Void> deliver(
			@ApiParam(value = "Order UUID", example = "f9981ca4-5a5e-4da3-af04-933861df3e55", required = true) String purchaseOrderUuid);

}
