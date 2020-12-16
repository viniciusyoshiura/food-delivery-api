package com.mycompany.fooddelivery.core.jackson;

import org.springframework.stereotype.Component;


import com.fasterxml.jackson.databind.module.SimpleModule;
import com.mycompany.fooddelivery.api.model.mixin.CityMixin;
import com.mycompany.fooddelivery.api.model.mixin.KitchenMixin;
import com.mycompany.fooddelivery.api.model.mixin.RestaurantMixin;
import com.mycompany.fooddelivery.domain.model.City;
import com.mycompany.fooddelivery.domain.model.Kitchen;
import com.mycompany.fooddelivery.domain.model.Restaurant;

// ---------- Class to register all Mixins
// ---------- A mixin is used to customize a class
@Component
public class JacksonMixinModule extends SimpleModule {

	private static final long serialVersionUID = 1L;

	public JacksonMixinModule() {
		setMixInAnnotation(Restaurant.class, RestaurantMixin.class);
		setMixInAnnotation(City.class, CityMixin.class);
	    setMixInAnnotation(Kitchen.class, KitchenMixin.class);
	}
	
}
