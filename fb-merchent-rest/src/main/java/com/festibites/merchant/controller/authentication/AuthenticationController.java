package com.festibites.merchant.controller.authentication;

import com.festibites.merchant.service.UserService;
import com.festibites.merchant.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.festibites.merchant.model.authentication.AuthenticationRequest;
import com.festibites.merchant.model.authentication.AuthenticationResponse;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class AuthenticationController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtTokenUtil;

//    @PostMapping("/authenticate")
//    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
//    	try {
//    		User user = userService.authenticateUser(authenticationRequest.getEmail(), authenticationRequest.getPassword());
//    		UserDTO userDTO = new UserDTO(user);
//    		return ResponseEntity.ok(userDTO);
//
//    	} catch (UserNotFoundException e) {
//            return ResponseEntity.status(404).body(e.getMessage());
//        } catch (InvalidCredentialsException e) {
//            return ResponseEntity.status(401).body(e.getMessage());
//        } catch (Exception e) {
//            // General error handling
//            return ResponseEntity.status(500).body("An error occurred during login");
//        }
//    }
    
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest) throws Exception 
    {
    	try {
    		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
    	}
    	catch(BadCredentialsException badCredentialsException){
    		throw(badCredentialsException);
    	}
    	
    	final UserDetails user = userService.loadUserByUsername(authenticationRequest.getEmail());
    	final String jwt = jwtTokenUtil.generateToken(user.getUsername());
    	return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
