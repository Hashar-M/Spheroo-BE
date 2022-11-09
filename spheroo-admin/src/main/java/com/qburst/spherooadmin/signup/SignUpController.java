package com.qburst.spherooadmin.signup;

import com.qburst.spherooadmin.user.Users;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Akhilesh
 * controller for sign up service.
 * It takes the request for a new user registration, validate the user and check for an existing user of same email id.
 * If the user email is doesn't match with any other it will create a new user.
 */
@RestController
@AllArgsConstructor
public class SignUpController {
    private SignUpService userService;

    /**
     * @param users - user model.
     * @return successful http response with body of ResponseDTO object in Json,
     * describing the status of new user creation.
     */
    @PostMapping("/registration")
    public ResponseEntity<ResponseDTO> registration(@Valid @RequestBody Users users ){
        if(userService.emailVerification(users.getEmailId())) {
            ResponseDTO response=new ResponseDTO(false,"email already in use",null);
            return ResponseEntity.ok(response);
        }
        else {
            userService.createNewUser(users);
            ResponseDTO response=new ResponseDTO(true,"success",null);
            return ResponseEntity.ok(response);
        }
    }

}
