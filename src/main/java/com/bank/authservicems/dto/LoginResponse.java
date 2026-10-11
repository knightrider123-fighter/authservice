
package com.bank.authservicems.dto;

public record LoginResponse(
		 String message,
	        String username,
	        boolean authenticated,
	        String accessToken,
	        String tokenType,
	        long expiresIn
) {}
