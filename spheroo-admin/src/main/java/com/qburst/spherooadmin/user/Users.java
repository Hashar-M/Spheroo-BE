package com.qburst.spherooadmin.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class Users
{
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "login_id")
//    private int  loginId;

    @Id
    @Column(name="user_id")
    private long userId;

    @Column(name="email_id")
    private String emailId;

    @Column(name="password")
    private String password;

    @OneToOne
    @MapsId
    @JoinColumn(name="user_id")
    private UserDetails userDetails;

}
