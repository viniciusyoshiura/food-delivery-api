package com.mycompany.fooddelivery.domain.service;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.mycompany.fooddelivery.domain.exception.BusinessException;
import com.mycompany.fooddelivery.domain.exception.EntityInUseException;
import com.mycompany.fooddelivery.domain.exception.UserNotFoundException;
import com.mycompany.fooddelivery.domain.model.User;
import com.mycompany.fooddelivery.domain.repository.UserRepository;

@Service
public class UserRegistrationService {

	private static final String MSG_USER_IN_USE = "User with code %d could not be removed, since it is in use";
	private static final String MSG_EMAIL_IN_USE = "There is already a user registered with the email %s";
	private static final String MSG_PASSWORD_MISMATCH = "Current password entered does not match the user's password.";
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EntityManager manager;
	
	@Transactional
	public User save(User user) {

		// ---------- Remove user from the persistence context
		manager.detach(user);

		Optional<User> existingUser = userRepository.findByEmail(user.getEmail());

		if (existingUser.isPresent() && !existingUser.get().equals(user)) {
			throw new BusinessException(
					String.format(MSG_EMAIL_IN_USE, user.getEmail()));
		}

		return userRepository.save(user);
	}

	@Transactional
	public void changePassword(Long userId, String currentPassword, String newPassword) {
		User user = searchOrFail(userId);

		if (user.passwordNotEquals(currentPassword)) {
			throw new BusinessException(MSG_PASSWORD_MISMATCH);
		}

		user.setPassword(newPassword);
	}

	public User searchOrFail(Long userId) {
		return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
	}

	@Transactional
	public void remove(Long userId) {
		try {
			userRepository.deleteById(userId);
			// ---------- Flushes all pending changes into database
			userRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new UserNotFoundException(userId);

		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(String.format(MSG_USER_IN_USE, userRepository));
		}
	}

}
