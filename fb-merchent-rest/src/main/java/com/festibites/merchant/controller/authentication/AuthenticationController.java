package com.festibites.merchant.controller.authentication;

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
import com.festibites.merchant.service.user.UserService;

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
