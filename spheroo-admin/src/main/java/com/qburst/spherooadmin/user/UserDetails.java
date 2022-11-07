package com.qburst.spherooadmin.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="user_details")
public class UserDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "last_name")
    private String lastName;


    @OneToOne(mappedBy = "userDetails",cascade = CascadeType.ALL)
//    @JoinColumn(name = "login_id",referencedColumnName = "login_id")
    @PrimaryKeyJoinColumn
    private Users users;

    @ManyToOne
    @JoinColumn(name="user_role_id")
    private UserRoles userRole;

}
