package com.bank.authservicems.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.authservicems.entity.AuthUser;

public interface AuthUserRepository
        extends JpaRepository<AuthUser, Long> {

    Optional<AuthUser> findByCustomerId(String customerId);

    Optional<AuthUser> findByUsername(String username);

    boolean existsByCustomerId(String customerId);

    boolean existsByUsername(String username);
}