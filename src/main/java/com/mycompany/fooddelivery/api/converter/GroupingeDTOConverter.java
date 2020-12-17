package com.mycompany.fooddelivery.api.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mycompany.fooddelivery.api.model.dto.GroupingeDTO;
import com.mycompany.fooddelivery.domain.model.Groupinge;

@Component
public class GroupingeDTOConverter {

	@Autowired
    private ModelMapper modelMapper;
    
    public GroupingeDTO toModel(Groupinge group) {
        return modelMapper.map(group, GroupingeDTO.class);
    }
    
    public List<GroupingeDTO> toCollectionModel(List<Groupinge> groups) {
        return groups.stream()
                .map(group -> toModel(group))
                .collect(Collectors.toList());
    } 
	
}
