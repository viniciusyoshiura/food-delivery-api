package com.mycompany.fooddelivery.api.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CitySummaryDTO {
	
	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Curitiba")
	private String name;
	
	@ApiModelProperty(example = "Paraná")
	private String state;
	
}
