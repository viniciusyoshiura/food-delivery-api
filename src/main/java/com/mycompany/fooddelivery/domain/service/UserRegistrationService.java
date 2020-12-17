package com.mycompany.fooddelivery.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.mycompany.fooddelivery.domain.exception.BusinessException;
import com.mycompany.fooddelivery.domain.exception.EntityInUseException;
import com.mycompany.fooddelivery.domain.exception.StateNotFoundException;
import com.mycompany.fooddelivery.domain.exception.UserNotFoundException;
import com.mycompany.fooddelivery.domain.model.User;
import com.mycompany.fooddelivery.domain.repository.UserRepository;

@Service
public class UserRegistrationService {
	
	private static final String MSG_USER_IN_USE = "User with code %d could not be removed, since it is in use";
	
	@Autowired
    private UserRepository userRepository;
    
    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }
    
    @Transactional
    public void changePassword(Long userId, String currentPassword, String newPassword) {
        User user = searchOrFail(userId);
        
        if (user.passwordNotEquals(currentPassword)) {
            throw new BusinessException("Current password entered does not match the user's password.");
        }
        
        user.setPassword(newPassword);
    }

    public User searchOrFail(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(userId));
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
