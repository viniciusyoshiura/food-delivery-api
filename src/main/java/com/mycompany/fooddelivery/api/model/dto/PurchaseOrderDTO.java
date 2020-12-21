package com.mycompany.fooddelivery.api.model.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PurchaseOrderDTO {

	private String uuid;
    private BigDecimal subtotal;
    private BigDecimal shippingFee;
    private BigDecimal totalValue;
    private String status;
    private OffsetDateTime dateRegister;
    private OffsetDateTime dateConfirmation;
    private OffsetDateTime dateDelivery;
    private OffsetDateTime dateCancellation;
    private RestaurantSummaryDTO restaurant;
    private UserDTO user;
    private PaymentMethodDTO paymentMethod;
    private AddressDTO deliverAddress;
    private List<ItemPurchaseOrderDTO> items;   
	
}
