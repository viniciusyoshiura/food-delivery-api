package com.mycompany.fooddelivery.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddressInput {

	@NotBlank
	private String zipCode;
	
	@NotBlank
	private String street;
	
	@NotBlank
	private String number;
	
	private String complement;
	
	@NotBlank
	private String district;
	
	@Valid
	@NotNull
	private CityIdInput city;
	
}
