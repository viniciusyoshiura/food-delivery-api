package com.mycompany.fooddelivery.api.v1.model.dto;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "users")
@Setter
@Getter
public class UserDTO extends RepresentationModel<UserDTO>{
	
	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Sherlock Holmes")
    private String name;
	
	@ApiModelProperty(example = "sherlock_holmes@gmail.com")
    private String email; 
	
}
