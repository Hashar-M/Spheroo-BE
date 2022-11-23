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

import static com.qburst.spherooadmin.constants.UserModelConstants.PASSWORD;
import static com.qburst.spherooadmin.constants.UserModelConstants.USERS_TABLE;
import static com.qburst.spherooadmin.constants.UserModelConstants.USER_EMAIL_ID;
import static com.qburst.spherooadmin.constants.UserModelConstants.USER_ID;
import static com.qburst.spherooadmin.constants.UserModelConstants.USER_NAME;
import static com.qburst.spherooadmin.constants.UserModelConstants.USER_ROLE;

/**
 * User model class.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = USERS_TABLE)
public class Users
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name=USER_ID)
    private long userId;

    @NotNull
    @NotEmpty
    @Column(name = USER_NAME, nullable = false)
    @Size(min = 3,max = 20)
    private String userName;

    @NotNull
    @NotEmpty
    @Email
    @Size(min =3,max = 320)
    @Column(name=USER_EMAIL_ID, nullable = false, unique = true, length = 320)
    private String emailId;

    @NotNull
    @NotEmpty
    @Column(name=PASSWORD,length = 20,nullable = false)
    @Size(min = 8,max = 15)
    private String password;

    @Column(name=USER_ROLE, nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private UserRole userRole;
}
