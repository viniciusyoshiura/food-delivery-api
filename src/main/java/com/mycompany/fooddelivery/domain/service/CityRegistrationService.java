package com.mycompany.fooddelivery.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.fooddelivery.domain.exception.CityNotFoundException;
import com.mycompany.fooddelivery.domain.exception.EntityInUseException;
import com.mycompany.fooddelivery.domain.model.City;
import com.mycompany.fooddelivery.domain.model.State;
import com.mycompany.fooddelivery.domain.repository.CityRepository;

@Service
public class CityRegistrationService {

	private static final String MSG_CITY_IN_USE = "City with code %d could not be removed, since it is in use";

	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	private StateRegistrationService stateRegistrationService;

	public City searchOrFail(Long cityId) {
		return cityRepository.findById(cityId)
				.orElseThrow(() -> new CityNotFoundException(cityId));
	}

	@Transactional
	public City save(City city) {
		Long stateId = city.getState().getId();
		State state = stateRegistrationService.searchOrFail(stateId);

		city.setState(state);

		return cityRepository.save(city);
	}

	@Transactional
	public void remove(Long cityId) {
		try {
			cityRepository.deleteById(cityId);
			// ---------- Flushes all pending changes into database
			cityRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new CityNotFoundException(cityId);

		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(
					String.format(MSG_CITY_IN_USE, cityId));
		}
	}

}
