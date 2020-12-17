package com.mycompany.fooddelivery.api.deconverter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mycompany.fooddelivery.api.model.input.UserInput;
import com.mycompany.fooddelivery.domain.model.User;

@Component
public class UserInputDeconverter {

	@Autowired
    private ModelMapper modelMapper;
    
    public User toDomainObject(UserInput userInput) {
        return modelMapper.map(userInput, User.class);
    }
    
    public void copyToDomainObject(UserInput userInput, User user) {
        modelMapper.map(userInput, user);
    }    
	
}
