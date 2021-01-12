package com.mycompany.fooddelivery.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.fooddelivery.api.converter.StateDTOConverter;
import com.mycompany.fooddelivery.api.deconverter.StateInputDeconverter;
import com.mycompany.fooddelivery.api.model.dto.StateDTO;
import com.mycompany.fooddelivery.api.model.input.StateInput;
import com.mycompany.fooddelivery.api.openapi.controller.StateControllerOpenApi;
import com.mycompany.fooddelivery.domain.model.State;
import com.mycompany.fooddelivery.domain.repository.StateRepository;
import com.mycompany.fooddelivery.domain.service.StateRegistrationService;

@RestController
@RequestMapping(path = "/states", produces = MediaType.APPLICATION_JSON_VALUE)
public class StateController implements StateControllerOpenApi {

	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private StateRegistrationService stateRegistrationService;
	
	@Autowired
	private StateDTOConverter stateDTOConverter;

	@Autowired
	private StateInputDeconverter stateInputDeconverter; 
	
	@GetMapping
	public List<StateDTO> list() {
		List<State> allStates =  stateRepository.findAll();
		
		 return stateDTOConverter.toCollectionModel(allStates);
	}

	@GetMapping("/{stateId}")
	public StateDTO search(@PathVariable Long stateId) {
		State state = stateRegistrationService.searchOrFail(stateId);
		
		return stateDTOConverter.toModel(state);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public StateDTO insert(@RequestBody @Valid StateInput stateInput) {
		State state = stateInputDeconverter.toDomainObject(stateInput);
		
		state = stateRegistrationService.save(state);
		
		return stateDTOConverter.toModel(state);
	}

	@PutMapping("/{stateId}")
	public StateDTO update(@PathVariable Long stateId, @RequestBody @Valid StateInput stateInput) {

		State currentState = stateRegistrationService.searchOrFail(stateId);
	    
		stateInputDeconverter.copyToDomainObject(stateInput, currentState);
	    
		currentState = stateRegistrationService.save(currentState);
	    
	    return stateDTOConverter.toModel(currentState);
		
	}

	@DeleteMapping("/{stateId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remove(@PathVariable Long stateId) {
		stateRegistrationService.remove(stateId);
	}

}
