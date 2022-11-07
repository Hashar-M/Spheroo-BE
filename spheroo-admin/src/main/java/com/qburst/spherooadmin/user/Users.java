package com.qburst.spherooadmin.user;

import com.sun.istack.NotNull;
import lombok.*;

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
    @Column(name="user_id")
    private Long userId;

    @Column(name="email_id")
    @NotNull
    private String emailId;

    @Column(name="password")
    @NotNull
    private String password;

    @OneToOne
    @MapsId
    @JoinColumn(name="user_id")
    private UserDetails userDetails;

}
