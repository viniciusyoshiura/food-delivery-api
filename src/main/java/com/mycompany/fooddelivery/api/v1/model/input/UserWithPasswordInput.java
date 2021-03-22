package com.mycompany.fooddelivery.api.v1.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserWithPasswordInput extends UserInput{
	
	@ApiModelProperty(example = "123", required = true)
	@NotBlank
	private String password;

}
