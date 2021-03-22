package com.mycompany.fooddelivery.api.v1.model.input;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ItemPurchaseOrderInput {
	
	@ApiModelProperty(example = "1", required = true)
	@NotNull
    private Long productId;
    
	@ApiModelProperty(example = "2", required = true)
    @NotNull
    @PositiveOrZero
    private Integer quantity;
    
	@ApiModelProperty(example = "Less spicy, please")
    private String observation;  
	
}
