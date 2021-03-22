package com.mycompany.fooddelivery.api.v1.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mycompany.fooddelivery.api.v1.model.input.KitchenInput;
import com.mycompany.fooddelivery.domain.model.Kitchen;

@Component
public class KitchenInputConverter {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public KitchenInput toKitchenInput(Kitchen kitchen) {
//		KitchenInput kitchenInput = new KitchenInput();
//		kitchenInput.setId(kitchen.getId());
//		
//		return kitchenInput;
		
		return modelMapper.map(kitchen, KitchenInput.class);
	}
	
}
