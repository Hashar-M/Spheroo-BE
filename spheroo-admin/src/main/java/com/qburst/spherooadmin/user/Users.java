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

import java.time.LocalDateTime;
import java.util.Date;

import static com.qburst.spherooadmin.constants.UserModelConstants.LAST_LOGIN;
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
    /**
     * The id of the user
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name=USER_ID)
    private long userId;

    /**
     * The userName of the user Entity.
     * It is used as purely a display name
     */
    @NotNull
    @NotEmpty
    @Column(name = USER_NAME, nullable = false)
    @Size(min = 3,max = 20)
    private String userName;

    /**
     * The emailId of the User
     */
    @NotNull
    @NotEmpty
    @Email
    @Size(min =3,max = 320)
    @Column(name=USER_EMAIL_ID, nullable = false, unique = true, length = 320)
    private String emailId;

    /**
     * The password of the user
     */
    @NotNull
    @NotEmpty
    @Column(name=PASSWORD,nullable = false)
    @Size(min = 8)
    private String password;

    /**
     * The role of the user, depending on their role they will have access to different features
     */
    @Column(name=USER_ROLE, nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private UserRole userRole;

    /**
     * The Date and Time when the user last logged im
     */
    @Column(name=LAST_LOGIN)
    private Date lastLogin;
}
