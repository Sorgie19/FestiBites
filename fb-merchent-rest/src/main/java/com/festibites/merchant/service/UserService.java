package com.festibites.merchant.service;

import com.festibites.merchant.exception.DuplicateEmailException;
import com.festibites.merchant.exception.InvalidCredentialsException;
import com.festibites.merchant.exception.InvalidInputException;
import com.festibites.merchant.exception.UserNotFoundException;
import com.festibites.merchant.exception.UserRegistrationException;
import com.festibites.merchant.model.User;
import com.festibites.merchant.repository.UserRepository;
import com.festibites.merchant.util.StringValidator;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public User register(User user) {
		// Check if the email is valid
		if (!StringValidator.isValidEmail(user.getEmail())) {
			throw new UserRegistrationException("Invalid email address.");
		}

		// Check if the name is valid
		if (!StringValidator.isValidName(user.getFirstName() + " " + user.getLastName())) {
			throw new UserRegistrationException("Invalid name.");
		}

		// Check if the email already exists
		if (emailExists(user.getEmail())) {
			throw new UserRegistrationException("Email already in use.");
		}

		// Check if the phone number already exists
		if (phoneNumberExists(user.getPhoneNumber())) {
			throw new UserRegistrationException("Phone number already in use.");
		}

		// Additional validation (e.g., password strength, etc.) can be added here

		// Encrypt the user's password
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		// Save the user to the database
		User newUser = userRepository.save(user);

		// Send a confirmation email (not implemented in this example)

		return newUser;
	}

	public User getUserById(Long userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found with id " + userId));
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User updateUser(Long userId, User updatedUser) {
		User existingUser = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found with id " + userId));

		// Check if the updated email address is already in use
		if (!existingUser.getEmail().equals(updatedUser.getEmail())
				&& userRepository.findByEmail(updatedUser.getEmail()) != null) {
			throw new DuplicateEmailException("Email address is already in use");
		}

		// Validate the first and last name
		if (StringValidator.isBlank(updatedUser.getFirstName())) {
			throw new InvalidInputException("First name cannot be blank");
		}
		if (StringValidator.isBlank(updatedUser.getLastName())) {
			throw new InvalidInputException("Last name cannot be blank");
		}
		if (!StringValidator.isValidName(updatedUser.getFirstName() + " " + updatedUser.getLastName())) {
			throw new InvalidInputException("Invalid name format");
		}

		// Validate the phone number
		if (StringValidator.isBlank(updatedUser.getPhoneNumber())) {
			throw new InvalidInputException("Phone number cannot be blank");
		}

		// Update the user details
		existingUser.setFirstName(updatedUser.getFirstName());
		existingUser.setLastName(updatedUser.getLastName());
		existingUser.setEmail(updatedUser.getEmail());
		existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
		// Add other fields to update as needed

		return userRepository.save(existingUser);
	}

	public User authenticateUser(String email, String password) {
		Optional<User> optionalUser = userRepository.findByEmail(email);

		User user = optionalUser.orElseThrow(() -> new UserNotFoundException("User not found with email " + email));

		if (!passwordEncoder.matches(password, user.getPassword())) {
			throw new InvalidCredentialsException("Invalid password for user " + email);
		}

		return user;
	}

	public void deleteUser(Long userId) {
		User existingUser = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found with id " + userId));

		userRepository.delete(existingUser);
	}
	private boolean emailExists(String email) {
		return userRepository.findByEmail(email).isPresent();
	}

	private boolean phoneNumberExists(String phoneNumber) {
		return userRepository.findByPhoneNumber(phoneNumber).isPresent();
	}

}
