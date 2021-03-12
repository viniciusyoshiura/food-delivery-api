package com.mycompany.fooddelivery.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.fooddelivery.api.converter.PermissionDTOConverter;
import com.mycompany.fooddelivery.api.model.dto.PermissionDTO;
import com.mycompany.fooddelivery.api.openapi.controller.PermissionControllerOpenApi;
import com.mycompany.fooddelivery.domain.model.Permission;
import com.mycompany.fooddelivery.domain.repository.PermissionRepository;

@RestController
@RequestMapping(path = "/permissions", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermissionController implements PermissionControllerOpenApi {

	@Autowired
    private PermissionRepository permissionRepository;
    
    @Autowired
    private PermissionDTOConverter permissionDTOConverter;
    
    @Override
    @GetMapping
    public CollectionModel<PermissionDTO> list() {
        List<Permission> allPermissions = permissionRepository.findAll();
        
        return permissionDTOConverter.toCollectionModel(allPermissions);
    }   
	
}
