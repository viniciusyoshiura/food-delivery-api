package com.mycompany.fooddelivery.api.v1.openapi.model;

import java.math.BigDecimal;

import com.mycompany.fooddelivery.api.v1.model.dto.KitchenDTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("RestaurantSummaryDTO")
@Setter
@Getter
public class RestaurantSummaryDTOOpenApi {

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Thai Gourmet")
	private String name;
	
	@ApiModelProperty(example = "12.00")
	private BigDecimal shippingFee;
	
	private KitchenDTO kitchen;
	
}
