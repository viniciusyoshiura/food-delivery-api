package com.mycompany.fooddelivery.api.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mycompany.fooddelivery.api.model.dto.StateDTO;
import com.mycompany.fooddelivery.domain.model.State;

@Component
public class StateDTOConverter {

	@Autowired
    private ModelMapper modelMapper;
    
    public StateDTO toModel(State state) {
        return modelMapper.map(state, StateDTO.class);
    }
    
    public List<StateDTO> toCollectionModel(List<State> states) {
        return states.stream()
                .map(state -> toModel(state))
                .collect(Collectors.toList());
    }
	
}
