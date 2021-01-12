package com.mycompany.fooddelivery.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddressInput {
	
	@ApiModelProperty(example = "38400-000", required = true)
	@NotBlank
	private String zipCode;
	
	@ApiModelProperty(example = "Baker Street", required = true)
	@NotBlank
	private String street;
	
	@ApiModelProperty(example = "\"221b\"", required = true)
	@NotBlank
	private String number;
	
	@ApiModelProperty(example = "Ap 221")
	private String complement;
	
	@ApiModelProperty(example = "Westminster", required = true)
	@NotBlank
	private String district;
	
	@Valid
	@NotNull
	private CityIdInput city;
	
}
