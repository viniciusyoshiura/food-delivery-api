package com.mycompany.fooddelivery.api.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.mycompany.fooddelivery.api.HateoasLinks;
import com.mycompany.fooddelivery.api.controller.StateController;
import com.mycompany.fooddelivery.api.model.dto.StateDTO;
import com.mycompany.fooddelivery.domain.model.State;

@Component
public class StateDTOConverter extends RepresentationModelAssemblerSupport<State, StateDTO>{

	@Autowired
    private ModelMapper modelMapper;
    
	@Autowired
	private HateoasLinks hateoasLinks;
	
	public StateDTOConverter() {
        super(StateController.class, StateDTO.class);
    }
	
    public StateDTO toModel(State state) {
    	
    	StateDTO stateDTO = createModelWithId(state.getId(), state);
        modelMapper.map(state, stateDTO);
        
        stateDTO.add(hateoasLinks.linkToStates("states"));
        
        return stateDTO;
    }
    
    @Override
    public CollectionModel<StateDTO> toCollectionModel(Iterable<? extends State> entities) {
    	return super.toCollectionModel(entities)
                .add(hateoasLinks.linkToStates());
    }
	
}
