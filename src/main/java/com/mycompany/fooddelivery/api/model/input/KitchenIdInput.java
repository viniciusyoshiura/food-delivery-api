package com.mycompany.fooddelivery.api.model.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KitchenIdInput {

	@NotNull
    private Long id;
	
}
