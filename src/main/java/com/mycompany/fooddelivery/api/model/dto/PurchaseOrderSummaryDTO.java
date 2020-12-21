package com.mycompany.fooddelivery.api.model.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PurchaseOrderSummaryDTO {

	private String uuid;
    private BigDecimal subtotal;
    private BigDecimal shippingFee;
    private BigDecimal totalValue;
    private String status;
    private OffsetDateTime dateRegister;
    private RestaurantSummaryDTO restaurant;
    private UserDTO user; 
	
}
