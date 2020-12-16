package com.mycompany.fooddelivery.api.deconverter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mycompany.fooddelivery.api.model.input.StateInput;
import com.mycompany.fooddelivery.domain.model.State;

@Component
public class StateInputDeconverter {

	@Autowired
    private ModelMapper modelMapper;
    
    public State toDomainObject(StateInput stateInput) {
        return modelMapper.map(stateInput, State.class);
    }
    
    public void copyToDomainObject(StateInput stateInput, State state) {
        modelMapper.map(stateInput, state);
    }
	
}
