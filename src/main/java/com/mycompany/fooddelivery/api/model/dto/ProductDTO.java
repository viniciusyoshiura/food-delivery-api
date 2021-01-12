package com.mycompany.fooddelivery.api.model.dto;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {
	
	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Cupim")
    private String name;
	
	@ApiModelProperty(example = "Type of meat")
    private String description;
	
	@ApiModelProperty(example = "12.50")
    private BigDecimal price;
	
	@ApiModelProperty(example = "true")
    private Boolean active; 
	
}
