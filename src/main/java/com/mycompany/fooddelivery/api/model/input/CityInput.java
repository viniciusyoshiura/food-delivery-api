package com.mycompany.fooddelivery.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CityInput {

	@NotBlank
    private String name;
    
    @Valid
    @NotNull
    private StateIdInput state;
	
}
