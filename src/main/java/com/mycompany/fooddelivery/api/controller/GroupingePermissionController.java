package com.mycompany.fooddelivery.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
	
	@GetMapping
	public List<PermissionDTO> list(@PathVariable Long groupId) {
		Groupinge groupinge = groupingeRegistrationService.searchOrFail(groupId);
		
		return permissionDTOConverter.toCollectionModel(groupinge.getPermissions());
	}
	
	@DeleteMapping("/{permissionId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void disassociate(@PathVariable Long groupId, @PathVariable Long permissionId) {
		groupingeRegistrationService.disassociatePermission(groupId, permissionId);
	}
	
	@PutMapping("/{permissionId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associate(@PathVariable Long groupId, @PathVariable Long permissionId) {
		groupingeRegistrationService.associatePermission(groupId, permissionId);
	}
	
}
