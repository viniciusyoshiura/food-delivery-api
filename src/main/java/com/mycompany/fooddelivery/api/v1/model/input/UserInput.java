package com.mycompany.fooddelivery.api.v1.model.input;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInput {
	
	@ApiModelProperty(example = "Sherlock Holmes", required = true)
	@NotBlank
    private String name;
    
	@ApiModelProperty(example = "sherlockholmes@bakerstreet.com", required = true)
    @NotBlank
    @Email
    private String email;
	
}
