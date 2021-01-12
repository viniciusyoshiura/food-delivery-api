package com.mycompany.fooddelivery.api.openapi.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.mycompany.fooddelivery.api.controller.exception.handler.Problem;
import com.mycompany.fooddelivery.api.model.dto.PurchaseOrderDTO;
import com.mycompany.fooddelivery.api.model.dto.PurchaseOrderSummaryDTO;
import com.mycompany.fooddelivery.api.model.input.PurchaseOrderInput;
import com.mycompany.fooddelivery.domain.filter.PurchaseOrderFilter;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Orders")
public interface PurchaseOrderControllerOpenApi {

	// ---------- ApiImplicitParams: includes Squiggly parameter in Swagger
	@ApiImplicitParams({
			@ApiImplicitParam(value = "Property names to filter in the response, separated by commas", name = "fields", paramType = "query", type = "string") })
	@ApiOperation("Search order with filter")
	Page<PurchaseOrderSummaryDTO> listWithFilters(PurchaseOrderFilter filter,
			@PageableDefault(size = 10) Pageable pageable);

	// ---------- ApiImplicitParams: includes Squiggly parameter in Swagger
	@ApiImplicitParams({
			@ApiImplicitParam(value = "Property names to filter in the response, separated by commas", name = "fields", paramType = "query", type = "string") })
	@ApiOperation("Search order by UUID")
	@ApiResponses({ @ApiResponse(code = 404, message = "Order not found", response = Problem.class) })
	PurchaseOrderDTO search(@PathVariable String purchaseOrderUuid);

	@ApiOperation("Registers an order")
	@ApiResponses({ @ApiResponse(code = 201, message = "Order registered"), })
	PurchaseOrderDTO insert(@Valid @RequestBody PurchaseOrderInput purchaseOrderInput);

}
