package com.mycompany.fooddelivery.api.v1.model.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "orders")
@Setter
@Getter
public class PurchaseOrderDTO extends RepresentationModel<PurchaseOrderDTO> {
	
	@ApiModelProperty(example = "f9981ca4-5a5e-4da3-af04-933861df3e55")
	private String uuid;
	
	@ApiModelProperty(example = "298.90")
    private BigDecimal subtotal;
	
	@ApiModelProperty(example = "10.00")
    private BigDecimal shippingFee;
	
	@ApiModelProperty(example = "308.90")
    private BigDecimal totalValue;
	
	@ApiModelProperty(example = "CREATED")
    private String status;
	
	@ApiModelProperty(example = "2021-01-01T00:00:01Z")
    private OffsetDateTime dateRegister;
	
	@ApiModelProperty(example = "2021-01-01T00:05:10Z")
    private OffsetDateTime dateConfirmation;
	
	@ApiModelProperty(example = "2021-01-01T00:55:30Z")
    private OffsetDateTime dateDelivery;
	
	@ApiModelProperty(example = "2021-01-01T20:35:00Z")
    private OffsetDateTime dateCancellation;
	
    private RestaurantOnlyNameDTO restaurant;
    private UserDTO user;
    private PaymentMethodDTO paymentMethod;
    private AddressDTO deliverAddress;
    private List<ItemPurchaseOrderDTO> items;   
	
}
