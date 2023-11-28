package com.festibites.merchant.controller.home;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.festibites.merchant.model.User;

@RestController
@RequestMapping("/")
public class HomeController 
{
	@GetMapping("/home")
	public String getHome() {
		return "Hello World";
	}
	
}
