package com.mycompany.fooddelivery.api.v1.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.mycompany.fooddelivery.api.v1.HateoasLinks;
import com.mycompany.fooddelivery.api.v1.controller.GroupingeController;
import com.mycompany.fooddelivery.api.v1.model.dto.GroupingeDTO;
import com.mycompany.fooddelivery.domain.model.Groupinge;

@Component
public class GroupingeDTOConverter extends RepresentationModelAssemblerSupport<Groupinge, GroupingeDTO> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private HateoasLinks hateoasLinks;

	public GroupingeDTOConverter() {
		super(GroupingeController.class, GroupingeDTO.class);
	}

	public GroupingeDTO toModel(Groupinge group) {
		GroupingeDTO groupDTO = createModelWithId(group.getId(), group);
		modelMapper.map(group, groupDTO);

		groupDTO.add(hateoasLinks.linkToGroupinges("groups"));

		groupDTO.add(hateoasLinks.linkToGroupingePermissions(group.getId(), "permissions"));

		return groupDTO;
	}

//    public List<GroupingeDTO> toCollectionModel(Collection<Groupinge> groups) {
//        return groups.stream()
//                .map(group -> toModel(group))
//                .collect(Collectors.toList());
//    }

	@Override
	public CollectionModel<GroupingeDTO> toCollectionModel(Iterable<? extends Groupinge> entities) {
		return super.toCollectionModel(entities).add(hateoasLinks.linkToGroupinges());
	}

}
