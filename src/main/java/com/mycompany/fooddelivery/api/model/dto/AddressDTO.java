package com.mycompany.fooddelivery.api.model.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddressDTO {

	private String zipCode;
	private String street;
	private String number;
	private String complement;
	private String district;
	private CitySummaryDTO city;
	
}
