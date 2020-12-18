package com.mycompany.fooddelivery.api.model.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestaurantDTO {

	private Long id;
	private String name;
	private BigDecimal shippingFee;
	private KitchenDTO kitchen;
	private Boolean active;
	private AddressDTO address;
	private Boolean open;
}
