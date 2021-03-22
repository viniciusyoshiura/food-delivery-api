package com.mycompany.fooddelivery.api.v1.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.mycompany.fooddelivery.api.v1.model.dto.UserDTO;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("UsersDto")
@Data
public class UsersDTOOpenApi {

	private UsersEmbeddedDTOOpenApi _embedded;
    private Links _links;
    
    @ApiModel("UsersEmbeddedDTOOpenApi")
    @Data
    public class UsersEmbeddedDTOOpenApi {
        
        private List<UserDTO> users;
        
    }   
	
}
