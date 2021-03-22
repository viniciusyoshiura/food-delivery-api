package com.mycompany.fooddelivery.api.v1.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaymentMethodInput {

	@ApiModelProperty(example = "Credit card", required = true)
	@NotBlank
    private String description;
	
}
