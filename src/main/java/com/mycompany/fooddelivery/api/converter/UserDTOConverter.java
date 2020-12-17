package com.mycompany.fooddelivery.api.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mycompany.fooddelivery.api.model.dto.UserDTO;
import com.mycompany.fooddelivery.domain.model.User;

@Component
public class UserDTOConverter {

	@Autowired
    private ModelMapper modelMapper;
    
    public UserDTO toModel(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
    
    public List<UserDTO> toCollectionModel(List<User> users) {
        return users.stream()
                .map(user -> toModel(user))
                .collect(Collectors.toList());
    }
	
}
