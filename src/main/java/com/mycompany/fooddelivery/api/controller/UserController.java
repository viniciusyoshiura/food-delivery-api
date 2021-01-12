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

import com.mycompany.fooddelivery.api.converter.UserDTOConverter;
import com.mycompany.fooddelivery.api.deconverter.UserInputDeconverter;
import com.mycompany.fooddelivery.api.model.dto.UserDTO;
import com.mycompany.fooddelivery.api.model.input.PasswordInput;
import com.mycompany.fooddelivery.api.model.input.UserInput;
import com.mycompany.fooddelivery.api.model.input.UserWithPasswordInput;
import com.mycompany.fooddelivery.api.openapi.controller.UserControllerOpenApi;
import com.mycompany.fooddelivery.domain.model.User;
import com.mycompany.fooddelivery.domain.repository.UserRepository;
import com.mycompany.fooddelivery.domain.service.UserRegistrationService;

@RestController
@RequestMapping(path = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController implements UserControllerOpenApi{

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserRegistrationService userRegistrationService;

	@Autowired
	private UserDTOConverter userDTOConverter;

	@Autowired
	private UserInputDeconverter userInputDeconverter;

	@GetMapping
	public List<UserDTO> list() {
		List<User> allUsers = userRepository.findAll();

		return userDTOConverter.toCollectionModel(allUsers);
	}

	@GetMapping("/{userId}")
	public UserDTO search(@PathVariable Long userId) {
		User user = userRegistrationService.searchOrFail(userId);

		return userDTOConverter.toModel(user);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UserDTO insert(@RequestBody @Valid UserWithPasswordInput userWithPasswordInput) {
		User user = userInputDeconverter.toDomainObject(userWithPasswordInput);
		user = userRegistrationService.save(user);

		return userDTOConverter.toModel(user);
	}

	@PutMapping("/{userId}")
	public UserDTO update(@PathVariable Long userId, @RequestBody @Valid UserInput userInput) {
		User currentUser = userRegistrationService.searchOrFail(userId);
		userInputDeconverter.copyToDomainObject(userInput, currentUser);
		currentUser = userRegistrationService.save(currentUser);

		return userDTOConverter.toModel(currentUser);
	}

	@PutMapping("/{userId}/password")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void changePassword(@PathVariable Long userId, @RequestBody @Valid PasswordInput passwordInput) {
		userRegistrationService.changePassword(userId, passwordInput.getCurrentPassword(),
				passwordInput.getNewPassword());
	}
	
	@DeleteMapping("/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remove(@PathVariable Long userId) {
		userRegistrationService.remove(userId);
	}

}
