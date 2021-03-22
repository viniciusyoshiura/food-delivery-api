package com.mycompany.fooddelivery.api.v1.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.mycompany.fooddelivery.api.v1.model.dto.GroupingeDTO;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("GroupsDTO")
@Data
public class GroupingesDTOOpenApi {

	private GroupingesEmbeddedDTOOpenApi _embedded;
    private Links _links;
    
    @ApiModel("GroupsEmbeddedDTOOpenApi")
    @Data
    public class GroupingesEmbeddedDTOOpenApi {
        
        private List<GroupingeDTO> groups;
        
    }
	
}
