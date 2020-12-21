package com.mycompany.fooddelivery.api.model.input;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ItemPurchaseOrderInput {

	@NotNull
    private Long productId;
    
    @NotNull
    @PositiveOrZero
    private Integer quantity;
    
    private String observation;  
	
}
