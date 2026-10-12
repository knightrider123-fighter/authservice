package com.bank.authservicems.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record OnboardRequest(

        @NotBlank(message = "Customer ID is required")
        @Size(max = 64, message = "Customer ID must not exceed 64 characters")
        @Pattern(
            regexp = "^[A-Za-z0-9_-]+$",
            message = "Customer ID contains invalid characters"
        )
        String customerId,

        @NotBlank(message = "Username is required")
        @Size(min = 4, max = 100,
              message = "Username must be between 4 and 100 characters")
        @Pattern(
            regexp = "^[A-Za-z0-9._-]+$",
            message = "Username may contain letters, numbers, dots, underscores and hyphens only"
        )
        String username

) {
}