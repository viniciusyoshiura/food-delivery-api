package com.mycompany.fooddelivery.api.v1.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CityInput {

	@ApiModelProperty(example = "São Paulo", required = true)
	@NotBlank
    private String name;
    
	@ApiModelProperty(example = "1")
    @Valid
    @NotNull
    private StateIdInput state;
	
}
