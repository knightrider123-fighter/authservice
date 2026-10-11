
package com.bank.authservicems.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/public/test")
    public String publicTest() {
        return "Public API is working!";
    }

    @GetMapping("/private/test")
    public String privateTest() {
        return "Private API is working!";
    }
}
