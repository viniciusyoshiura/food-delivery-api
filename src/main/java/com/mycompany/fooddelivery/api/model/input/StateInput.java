package com.mycompany.fooddelivery.api.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StateInput {
	
	@ApiModelProperty(example = "Mato Grosso do Sul", required = true)
	@NotBlank
    private String name;
	
}
