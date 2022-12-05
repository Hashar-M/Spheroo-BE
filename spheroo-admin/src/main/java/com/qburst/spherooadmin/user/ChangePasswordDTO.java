package com.qburst.spherooadmin.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * It carries the new password for user.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordDTO {
    private String password;
}
