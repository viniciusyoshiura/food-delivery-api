package com.mycompany.fooddelivery.api.model.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "orders")
@Setter
@Getter
public class PurchaseOrderSummaryDTO extends RepresentationModel<PurchaseOrderSummaryDTO> {
	
	
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
	
    private RestaurantOnlyNameDTO restaurant;
    private UserDTO user; 
	
}
