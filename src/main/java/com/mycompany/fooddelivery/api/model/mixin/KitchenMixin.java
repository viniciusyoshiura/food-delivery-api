package com.mycompany.fooddelivery.api.model.mixin;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mycompany.fooddelivery.domain.model.Restaurant;

public abstract class KitchenMixin {
	
	@JsonIgnore
	private List<Restaurant> restaurants;
	
}
