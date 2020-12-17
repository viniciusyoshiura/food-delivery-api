package com.mycompany.fooddelivery.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.mycompany.fooddelivery.domain.exception.EntityInUseException;
import com.mycompany.fooddelivery.domain.exception.GroupingeNotFoundException;
import com.mycompany.fooddelivery.domain.model.Groupinge;
import com.mycompany.fooddelivery.domain.repository.GroupingeRepository;

@Service
public class GroupingeRegistrationService {

	private static final String MSG_GROUPINGE_IN_USE = "Group with code %d could not be removed, since it is in use";

	@Autowired
	private GroupingeRepository groupingeRepository;

	@Transactional
	public Groupinge save(Groupinge groupinge) {
		return groupingeRepository.save(groupinge);
	}

	@Transactional
	public void remove(Long groupingeId) {
		try {
			groupingeRepository.deleteById(groupingeId);
			groupingeRepository.flush();

		} catch (EmptyResultDataAccessException e) {
			throw new GroupingeNotFoundException(groupingeId);

		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(String.format(MSG_GROUPINGE_IN_USE, groupingeId));
		}
	}

	public Groupinge searchOrFail(Long groupingeId) {
		return groupingeRepository.findById(groupingeId).orElseThrow(() -> new GroupingeNotFoundException(groupingeId));
	}

}
