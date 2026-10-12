
package com.bank.authservicems.entity;

import java.time.Instant;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "auth_users")
@Getter
@Setter
@NoArgsConstructor
public class AuthUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id", nullable = false,
            unique = true, length = 64)
    private String customerId;

    @Column(nullable = false, unique = true, length = 100)
    private String username;

    @Column(name = "password_hash", length = 255)
    private String passwordHash;

    @Column(nullable = false, length = 30)
    private String role = "CUSTOMER";

    @Column(name = "account_status",
            nullable = false, length = 40)
    private String accountStatus = "PASSWORD_SETUP_PENDING";

    @Column(nullable = false)
    private boolean enabled = false;

    @Column(name = "created_at", nullable = false,
            updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }
}
