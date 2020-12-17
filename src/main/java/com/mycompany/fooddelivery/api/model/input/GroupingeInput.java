package com.mycompany.fooddelivery.api.model.input;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupingeInput {

	@NotBlank
    private String name;
	
}
