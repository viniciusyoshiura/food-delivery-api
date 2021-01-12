package com.mycompany.fooddelivery.api.model.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.mycompany.fooddelivery.api.model.view.RestaurantView;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class KitchenDTO {
	
	@ApiModelProperty(example = "1")
	@JsonView({ RestaurantView.Summary.class})
	private Long id;
	
	@ApiModelProperty(example = "Brazilian")
	@JsonView({ RestaurantView.Summary.class})
	private String name;
	
}
