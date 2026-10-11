package com.bank.authservicems.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.authservicems.service.JwtService;

import lombok.Data;

@Data
@RestController
@RequestMapping("/v1/api/admin")
public class AdminController {
	@GetMapping("/dashboard")
	public String dashboard() {
		return "Welcome to the Admin Dashboard";
	}

}
