package com.mycompany.fooddelivery.api.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CityDTO {

	
	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Ribeirão Preto")
    private String name;
    
    private StateDTO state;
	
}
