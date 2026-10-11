package com.bank.authservicems.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import com.bank.authservicems.dto.LoginRequest;
import com.bank.authservicems.dto.LoginResponse;
import com.bank.authservicems.exception.InvalidCredentialsException;
import com.bank.authservicems.service.JwtService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RestController
@RequestMapping("/v1/api/auth")
@RequiredArgsConstructor
public class AuthController {
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	@Value("${jwt.access-token-minutes}")
	private long accessTokenMinutes;

	@PostMapping("/login")
	public LoginResponse login(@Valid @RequestBody LoginRequest request) {
		try {

			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
			String accessToken = jwtService.generateToken(authentication);
			return new LoginResponse("Login successful", authentication.getName(), true, accessToken, "Bearer",
					accessTokenMinutes * 60);
		} catch (AuthenticationException e) {
			 throw new InvalidCredentialsException();
		}

	}

}
