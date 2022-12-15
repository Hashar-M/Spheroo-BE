package com.qburst.spherooadmin.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Hameel
 * The PasswordResetToken entity is generated for each user whenever they request a password
 * reset through the forgot password page.
 */
@Entity
@Getter
@Setter
@Table(name = "password_reset_token")
public class PasswordResetToken {

    /**
     * The id for the reset token in the database table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * The generated reset token for the user
     */
    @Column(name = "token", nullable = false)
    private String token;

    /**
     * The PasswordResetToken has a One-to-One relation with a User entity
     */
    @OneToOne(targetEntity = Users.class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id")
    private Users user;

    /**
     * The expiration date for a token, after the expiration date, the token will no longer be valid.
     */
    @Column(name = "expiry_date", nullable = false)
    private Date expiryDate;
}
