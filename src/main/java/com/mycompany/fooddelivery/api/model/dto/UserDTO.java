package com.mycompany.fooddelivery.api.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDTO {
	
	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Sherlock Holmes")
    private String name;
	
	@ApiModelProperty(example = "sherlock_holmes@gmail.com")
    private String email; 
	
}
