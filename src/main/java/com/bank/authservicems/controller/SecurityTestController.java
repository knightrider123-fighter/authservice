
package com.bank.authservicems.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityTestController {

    @GetMapping("/private/me")
    public String currentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext()
                                     .getAuthentication();

        return "Principal: " + authentication.getName()
                + ", Authenticated: "
                + authentication.isAuthenticated();
    }
}
