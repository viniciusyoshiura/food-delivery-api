package com.mycompany.fooddelivery.api.converter;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mycompany.fooddelivery.api.model.dto.PermissionDTO;
import com.mycompany.fooddelivery.domain.model.Permission;

@Component
public class PermissionDTOConverter {

	@Autowired
    private ModelMapper modelMapper;
    
    public PermissionDTO toModel(Permission permission) {
        return modelMapper.map(permission, PermissionDTO.class);
    }
    
    public List<PermissionDTO> toCollectionModel(Collection<Permission> permissions) {
        return permissions.stream()
                .map(permission -> toModel(permission))
                .collect(Collectors.toList());
    }
	
}
