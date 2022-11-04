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
    private int userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;


//    @OneToOne(targetEntity = Users.class,cascade = CascadeType.ALL)
//    @JoinColumn(name = "user_id_foreign",referencedColumnName = "user_id")
//    private Users users;



}
