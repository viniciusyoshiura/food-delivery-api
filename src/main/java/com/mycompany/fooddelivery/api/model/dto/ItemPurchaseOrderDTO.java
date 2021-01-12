package com.mycompany.fooddelivery.api.model.dto;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ItemPurchaseOrderDTO {
	
	@ApiModelProperty(example = "1")
	private Long productId;
	
	@ApiModelProperty(example = "Pork with sweet and sour sauce")
    private String productName;
	
	@ApiModelProperty(example = "2")
    private Integer quantity;
	
	@ApiModelProperty(example = "78.90")
    private BigDecimal unitPrice;
	
	@ApiModelProperty(example = "157.80")
    private BigDecimal totalPrice;
	
	@ApiModelProperty(example = "Less spicy, please")
    private String observation;
	
}
