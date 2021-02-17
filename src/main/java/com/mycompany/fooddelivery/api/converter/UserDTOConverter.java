package com.mycompany.fooddelivery.api.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.mycompany.fooddelivery.api.HateoasLinks;
import com.mycompany.fooddelivery.api.controller.UserController;
import com.mycompany.fooddelivery.api.model.dto.UserDTO;
import com.mycompany.fooddelivery.domain.model.User;

@Component
public class UserDTOConverter extends RepresentationModelAssemblerSupport<User, UserDTO> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private HateoasLinks hateoasLinks;
	
	public UserDTOConverter() {
		super(UserController.class, UserDTO.class);
	}
	
	public UserDTO toModel(User user) {
		UserDTO userDTO = createModelWithId(user.getId(), user);
		modelMapper.map(user, userDTO);
		
		userDTO.add(hateoasLinks.linkToUsers("users"));
	    
		userDTO.add(hateoasLinks.linkToUserGroupinge(user.getId(), "user-groups"));

		return userDTO;

	}

	@Override
	public CollectionModel<UserDTO> toCollectionModel(Iterable<? extends User> entities) {
		return super.toCollectionModel(entities).add(hateoasLinks.linkToUsers());
	}

}
