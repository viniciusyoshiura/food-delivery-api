package com.mycompany.fooddelivery.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.fooddelivery.domain.exception.EntityInUseException;
import com.mycompany.fooddelivery.domain.exception.KitchenNotFoundException;
import com.mycompany.fooddelivery.domain.model.Kitchen;
import com.mycompany.fooddelivery.domain.repository.KitchenRepository;

@Service
public class KitchenRegistrationService {

	private static final String MSG_KITCHEN_IN_USE = "Kitchen with code %d could not be removed, since it is in use";
	@Autowired
	private KitchenRepository kitchenRepository;

	@Transactional
	public Kitchen save(Kitchen kitchen) {
		return kitchenRepository.save(kitchen);
	}

	@Transactional
	public void remove(Long kitchenId) {
		try {
			kitchenRepository.deleteById(kitchenId);
			// ---------- Flushes all pending changes into database
			kitchenRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new KitchenNotFoundException(kitchenId);

		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(String.format(MSG_KITCHEN_IN_USE, kitchenId));
		}
	}

	public Kitchen searchOrFail(Long kitchenId) {
		return kitchenRepository.findById(kitchenId).orElseThrow(() -> new KitchenNotFoundException(kitchenId));
	}

}
