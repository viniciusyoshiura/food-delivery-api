package com.mycompany.fooddelivery.api.deconverter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mycompany.fooddelivery.api.model.input.KitchenInput;
import com.mycompany.fooddelivery.domain.model.Kitchen;

@Component
public class KitchenInputDeconverter {

	@Autowired
    private ModelMapper modelMapper;
    
    public Kitchen toDomainObject(KitchenInput kitchenInput) {
        return modelMapper.map(kitchenInput, Kitchen.class);
    }
    
    public void copyToDomainObject(KitchenInput kitchenInput, Kitchen kitchen) {
        modelMapper.map(kitchenInput, kitchen);
    }
    
	
}
