package com.mycompany.fooddelivery.api.model.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestaurantIdInput {

	@NotNull
    private Long id;   
	
}
