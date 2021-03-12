package com.mycompany.fooddelivery.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.fooddelivery.api.HateoasLinks;
import com.mycompany.fooddelivery.api.converter.PermissionDTOConverter;
import com.mycompany.fooddelivery.api.model.dto.PermissionDTO;
import com.mycompany.fooddelivery.api.openapi.controller.GroupingePermissionControllerOpenApi;
import com.mycompany.fooddelivery.domain.model.Groupinge;
import com.mycompany.fooddelivery.domain.service.GroupingeRegistrationService;

@RestController
@RequestMapping(path = "/groups/{groupId}/permissions", produces = MediaType.APPLICATION_JSON_VALUE)
public class GroupingePermissionController implements GroupingePermissionControllerOpenApi{

	@Autowired
	private GroupingeRegistrationService groupingeRegistrationService;
	
	@Autowired
	private PermissionDTOConverter permissionDTOConverter;
	
	@Autowired
	private HateoasLinks hateoasLinks;
	
	@GetMapping
	public CollectionModel<PermissionDTO> list(@PathVariable Long groupId) {

		Groupinge groupinge = groupingeRegistrationService.searchOrFail(groupId);
	    
		CollectionModel<PermissionDTO> permissionsDTO 
	        = permissionDTOConverter.toCollectionModel(groupinge.getPermissions())
	            .removeLinks()
	            .add(hateoasLinks.linkToGroupingePermissions(groupId))
	            .add(hateoasLinks.linkToGroupingePermissionAssociation(groupId, "associate"));
	    
		permissionsDTO.getContent().forEach(permissionDTO -> {
			permissionDTO.add(hateoasLinks.linkToGroupingePermissionDisassociation(
					groupId, permissionDTO.getId(), "disassociate"));
	    });
	    
	    return permissionsDTO;
		
	}
	
	@DeleteMapping("/{permissionId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> disassociate(@PathVariable Long groupId, @PathVariable Long permissionId) {
		groupingeRegistrationService.disassociatePermission(groupId, permissionId);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{permissionId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> associate(@PathVariable Long groupId, @PathVariable Long permissionId) {
		groupingeRegistrationService.associatePermission(groupId, permissionId);
		return ResponseEntity.noContent().build();
	}
	
}
