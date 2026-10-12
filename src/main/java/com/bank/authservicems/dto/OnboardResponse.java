package com.bank.authservicems.dto;

public record OnboardResponse(String customerId, String username, String accountStatus, String message) {
}
