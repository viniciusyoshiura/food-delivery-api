package com.mycompany.fooddelivery.api.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddressDTO {
	
	@ApiModelProperty(example = "38400-000")
	private String zipCode;
	
	@ApiModelProperty(example = "Baker Street")
	private String street;
	
	@ApiModelProperty(example = "\"221b\"")
	private String number;
	
	@ApiModelProperty(example = "Apto 221")
	private String complement;
	
	@ApiModelProperty(example = "Westminster")
	private String district;
	
	private CitySummaryDTO city;
	
}
