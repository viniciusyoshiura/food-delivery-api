package com.mycompany.fooddelivery.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.fooddelivery.domain.exception.EntityInUseException;
import com.mycompany.fooddelivery.domain.exception.StateNotFoundException;
import com.mycompany.fooddelivery.domain.model.State;
import com.mycompany.fooddelivery.domain.repository.StateRepository;

@Service
public class StateRegistrationService {

	private static final String MSG_STATE_IN_USE = "State with code %d could not be removed, since it is in use";

//	private static final String MSG_STATE_NOT_FOUND = "There is not a state registration withe code %d";

	@Autowired
	private StateRepository stateRepository;

	public State searchOrFail(Long stateId) {
		return stateRepository.findById(stateId)
				.orElseThrow(() -> new StateNotFoundException(stateId));
	}

	@Transactional
	public State save(State state) {
		return stateRepository.save(state);
	}

	@Transactional
	public void remove(Long stateId) {
		try {
			stateRepository.deleteById(stateId);

		} catch (EmptyResultDataAccessException e) {
			throw new StateNotFoundException(stateId);

		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(String.format(MSG_STATE_IN_USE, stateId));
		}
	}

}
