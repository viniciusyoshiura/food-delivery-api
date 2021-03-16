package com.mycompany.fooddelivery.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.mycompany.fooddelivery.api.model.dto.PermissionDTO;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("PermissionsDTO")
@Data
public class PermissionsDTOOpenApi {

	private PermissionsEmbeddedDTOOpenApi _embedded;
    private Links _links;
    
    @ApiModel("PermissionsEmbeddedDTOOpenApi")
    @Data
    public class PermissionsEmbeddedDTOOpenApi {
        
        private List<PermissionDTO> permissions;
        
    }   
	
}
