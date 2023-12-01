package com.festibites.merchant.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.festibites.merchant.model.user.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);

	Optional<User> findByPhoneNumber(String phoneNumber);
	
	Optional<User> findById(Long id);

}