package com.mycompany.fooddelivery.api.openapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.mycompany.fooddelivery.api.model.dto.DailySaleDTO;
import com.mycompany.fooddelivery.domain.filter.DailySaleFilter;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Statistics")
public interface StatisticsControllerOpenApi {

	// ---------- Here we used @ApiImplicitParams, since DailySaleFilter is in a
	// domain package
	@ApiOperation("Query daily sales statistics")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "restaurantId", value = "Restaurant ID", example = "1", dataType = "int"),
			@ApiImplicitParam(name = "dateRegisterStart", value = "Datetime start of order creation", example = "2021-01-01T00:00:00Z", dataType = "date-time"),
			@ApiImplicitParam(name = "dateRegisterEnd", value = "Datetime end of order creation", example = "2021-01-01T23:59:59Z", dataType = "date-time") })
	public List<DailySaleDTO> queryDailySales(DailySaleFilter filter,
			@ApiParam(value = "Time shift to be considered in the query in relation to UTC", defaultValue = "+00:00") String timeOffset);

	public ResponseEntity<byte[]> queryDailySalesPdf(DailySaleFilter filter, String timeOffset);

}
