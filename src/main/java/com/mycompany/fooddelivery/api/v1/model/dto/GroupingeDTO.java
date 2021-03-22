package com.mycompany.fooddelivery.api.v1.model.dto;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "groups")
@Getter
@Setter
public class GroupingeDTO extends RepresentationModel<GroupingeDTO> {

	private Long id;
    private String name;
	
}
