package com.mycompany.fooddelivery.api.v1.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.mycompany.fooddelivery.api.v1.HateoasLinks;
import com.mycompany.fooddelivery.api.v1.model.dto.PermissionDTO;
import com.mycompany.fooddelivery.domain.model.Permission;

@Component
public class PermissionDTOConverter implements RepresentationModelAssembler<Permission, PermissionDTO> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private HateoasLinks hateoasLinks;

	public PermissionDTO toModel(Permission permission) {
		PermissionDTO permissionDTO = modelMapper.map(permission, PermissionDTO.class);
		return permissionDTO;
	}

//    public List<PermissionDTO> toCollectionModel(Collection<Permission> permissions) {
//        return permissions.stream()
//                .map(permission -> toModel(permission))
//                .collect(Collectors.toList());
//    }

	@Override
	public CollectionModel<PermissionDTO> toCollectionModel(Iterable<? extends Permission> entities) {
		return RepresentationModelAssembler.super.toCollectionModel(entities).add(hateoasLinks.linkToPermissions());
	}

}
