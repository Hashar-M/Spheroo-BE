package com.qburst.spherooadmin.signup;

import com.qburst.spherooadmin.user.Users;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class SignUpController {
    private SignUpService userService;
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
