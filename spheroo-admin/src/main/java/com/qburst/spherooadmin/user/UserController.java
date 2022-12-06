package com.qburst.spherooadmin.user;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@AllArgsConstructor
public class UserController {
    private UserService userService;

    /**
     * method for changing password of a user.
     * @param request
     * @param changePasswordDTO has the new password of {@link Users}
     * @return Http status code 204 for successful update of {@link Users} password.
     */
    @PutMapping("/change-password")
    public ResponseEntity<Object> changeUserPassword(HttpServletRequest request, @RequestBody ChangePasswordDTO changePasswordDTO){
        /**
         * Accessing the Authentication object in {@link SecurityContextHolder} for getting details of currently logging {@link Users}
         */
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String emailId=authentication.getName();

        String newPassword= changePasswordDTO.getPassword();

        userService.changePassword(emailId,newPassword);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
