package com.qburst.spherooadmin.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Users
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "login_id")
    private int  loginId;

    @Column(name="email_id")
    private String emailId;

    @Column(name="password")
    private String password;

//    @OneToOne
//    @JoinColumn(name = "user_id_foreign",referencedColumnName = "user_id")
//    private Users users;
}
