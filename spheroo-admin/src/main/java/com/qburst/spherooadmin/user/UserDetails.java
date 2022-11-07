package com.qburst.spherooadmin.user;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
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
    @NotNull
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "last_name")
    private String lastName;


    @OneToOne(mappedBy = "userDetails",cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Users users;

    @ManyToOne
    @JoinColumn(name="user_role_id")
    private UserRoles userRole;

}
