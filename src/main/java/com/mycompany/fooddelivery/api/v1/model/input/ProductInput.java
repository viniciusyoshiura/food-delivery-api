package com.mycompany.fooddelivery.api.v1.model.input;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductInput {
	
	@ApiModelProperty(example = "Cupim", required = true)
	@NotBlank
    private String name;
    
	@ApiModelProperty(example = "Type of meat", required = true)
    @NotBlank
    private String description;
    
	@ApiModelProperty(example = "12.50", required = true)
    @NotNull
    @PositiveOrZero
    private BigDecimal price;
    
	@ApiModelProperty(example = "true", required = true)
    @NotNull
    private Boolean active;
	
}
