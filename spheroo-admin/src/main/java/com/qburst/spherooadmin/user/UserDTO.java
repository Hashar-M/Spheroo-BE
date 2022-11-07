package com.qburst.spherooadmin.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO
{
    private String firstName;
    private String secondName;
    private String lastName;
    private String emailId;
    private String password;

}
