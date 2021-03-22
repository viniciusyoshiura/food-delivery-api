package com.mycompany.fooddelivery.api.v1.deconverter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mycompany.fooddelivery.api.v1.model.input.GroupingeInput;
import com.mycompany.fooddelivery.domain.model.Groupinge;

@Component
public class GroupingeInputDeconverter {

	@Autowired
    private ModelMapper modelMapper;
    
    public Groupinge toDomainObject(GroupingeInput groupInput) {
        return modelMapper.map(groupInput, Groupinge.class);
    }
    
    public void copyToDomainObject(GroupingeInput groupInput, Groupinge group) {
        modelMapper.map(groupInput, group);
    }   
	
}
