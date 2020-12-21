package com.mycompany.fooddelivery.api.model.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.mycompany.fooddelivery.api.model.view.RestaurantView;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class KitchenDTO {
	
	@JsonView({ RestaurantView.Summary.class})
	private Long id;
	
	@JsonView({ RestaurantView.Summary.class})
	private String name;
	
}
