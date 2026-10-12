package com.bank.authservicems.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.bank.authservicems.dto.OnboardRequest;
import com.bank.authservicems.dto.OnboardResponse;
import com.bank.authservicems.entity.AuthUser;
import com.bank.authservicems.repository.AuthUserRepository;

@Service
public class AuthOnboardingService {

    private final AuthUserRepository authUserRepository;

    public AuthOnboardingService(AuthUserRepository authUserRepository) {
        this.authUserRepository = authUserRepository;
    }

    @Transactional
    public OnboardResponse onboardCustomer(OnboardRequest request) {

        // 1. Check whether this customer already has a login identity
        var existingCustomer =
                authUserRepository.findByCustomerId(request.customerId());

        if (existingCustomer.isPresent()) {
            AuthUser existing = existingCustomer.get();

            // Allow a retry of the exact same onboarding request
            if (existing.getUsername().equals(request.username())) {
                return new OnboardResponse(
                        existing.getCustomerId(),
                        existing.getUsername(),
                        existing.getAccountStatus(),
                        "Login identity already exists"
                );
            }

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Customer already has a login identity"
            );
        }

        // 2. Check whether the username is already taken
        if (authUserRepository.existsByUsername(request.username())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Username is already taken"
            );
        }

        // 3. Create the login identity
        AuthUser user = new AuthUser();
        user.setCustomerId(request.customerId());
        user.setUsername(request.username());
        user.setPasswordHash(null);
        user.setRole("CUSTOMER");
        user.setAccountStatus("PASSWORD_SETUP_PENDING");
        user.setEnabled(false);

        // 4. Save the identity in the database
        AuthUser savedUser = authUserRepository.save(user);

        // 5. Return a response DTO
        return new OnboardResponse(
                savedUser.getCustomerId(),
                savedUser.getUsername(),
                savedUser.getAccountStatus(),
                "Login identity created; password setup is pending"
        );
    }
}