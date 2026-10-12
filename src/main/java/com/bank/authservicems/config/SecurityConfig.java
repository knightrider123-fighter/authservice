
package com.bank.authservicems.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import com.bank.authservicems.security.CustomAuthenticationEntryPoint;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomAuthenticationEntryPoint authenticationEntryPoint;

    // Converts JWT authorities claim into Spring Security authorities
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {

        JwtAuthenticationConverter converter =
                new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            List<String> authorities =
                    jwt.getClaimAsStringList("authorities");

            if (authorities == null) {
                return List.of();
            }

            return authorities.stream()
                    .<GrantedAuthority>map(SimpleGrantedAuthority::new)
                    .toList();
        });

        return converter;
    }

    // Bean 1: H2 console security
    @Bean
    @Order(1)
    public SecurityFilterChain h2ConsoleFilterChain(
            HttpSecurity http) throws Exception {

        http
            .securityMatcher("/h2-console", "/h2-console/**")
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth ->
                    auth.anyRequest().permitAll()
            )
            .headers(headers ->
                    headers.frameOptions(frame -> frame.sameOrigin())
            );

        return http.build();
    }

    // Bean 2: Main API and JWT security
    @Bean
    @Order(2)
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth.requestMatchers("/favicon.ico").permitAll()
            		.requestMatchers("/internal/auth/onboard").permitAll()
                .requestMatchers("/error").permitAll()

                .requestMatchers(
                    HttpMethod.POST, "/v1/api/auth/login"
                ).permitAll()

                .requestMatchers("/public/**").permitAll()

                .requestMatchers("/v1/api/admin/**")
                    .hasRole("ADMIN")

                .anyRequest().authenticated()
            )

            .httpBasic(basic -> basic.disable())
            .formLogin(form -> form.disable())

            .headers(headers ->
                    headers.frameOptions(frame -> frame.sameOrigin())
            )

            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt
                    .jwtAuthenticationConverter(
                        jwtAuthenticationConverter()
                    )
                )
                .authenticationEntryPoint(authenticationEntryPoint)
            );

        return http.build();
    }
}
