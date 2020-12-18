package com.mycompany.fooddelivery.api.model.input;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductInput {

	@NotBlank
    private String name;
    
    @NotBlank
    private String description;
    
    @NotNull
    @PositiveOrZero
    private BigDecimal price;
    
    @NotNull
    private Boolean active;
	
}
