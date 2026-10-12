package com.bank.authservicems.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.authservicems.dto.OnboardRequest;
import com.bank.authservicems.dto.OnboardResponse;
import com.bank.authservicems.service.AuthOnboardingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/internal/auth")
public class AuthOnboardingController {

    private final AuthOnboardingService authOnboardingService;

    public AuthOnboardingController(
            AuthOnboardingService authOnboardingService) {
        this.authOnboardingService = authOnboardingService;
    }

    @PostMapping("/onboard")
    public ResponseEntity<OnboardResponse> onboardCustomer(
            @Valid @RequestBody OnboardRequest request) {

        OnboardResponse response =
                authOnboardingService.onboardCustomer(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}