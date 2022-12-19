package com.qburst.spherooadmin.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    /**
     * Find a password reset token by the token value
     * @param token The password reset token to search the DB by
     * @return The PasswordResetToken entity with the corresponding token
     */
    PasswordResetToken findPasswordResetTokenByToken(String token);
}