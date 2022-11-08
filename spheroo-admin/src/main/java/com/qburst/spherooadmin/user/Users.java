package com.qburst.spherooadmin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name="email_id", nullable = false, unique = true, length = 320)
    private String emailId;

    @Column(name="password",nullable = false)
    private String password;

    @Column(name="user_role", nullable = false)
    private int userRole;

}
