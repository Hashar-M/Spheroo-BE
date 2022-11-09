package com.qburst.spherooadmin.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * User model class.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class Users
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private long userId;

    @NotNull
    @NotEmpty
    @Column(name = "user_name", nullable = false)
    @Size(min = 6,max = 30)
    private String userName;

    @NotNull
    @NotEmpty
    @Email
    @Size(min =3,max = 320)
    @Column(name="email_id", nullable = false, unique = true, length = 320)
    private String emailId;

    @NotNull
    @NotEmpty
    @Column(name="password",length = 20,nullable = false)
    @Size(min = 8,max = 100)
    private String password;

    @Column(name="user_role", nullable = false)
    @Enumerated(EnumType.ORDINAL)

    private UserRole userRole;
}
