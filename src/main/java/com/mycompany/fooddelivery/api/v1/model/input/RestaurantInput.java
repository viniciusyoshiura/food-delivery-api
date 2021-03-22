package com.mycompany.fooddelivery.api.v1.model.input;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestaurantInput {
	
	@ApiModelProperty(example = "Thai Gourmet", required = true)
	@NotBlank
	private String name;
	
	@ApiModelProperty(example = "12.00", required = true)
	@NotNull
	@PositiveOrZero
	private BigDecimal shippingFee;
	
	@Valid
	@NotNull
	private KitchenIdInput kitchen;
	
	@Valid
	@NotNull
	private AddressInput address;
	
}
