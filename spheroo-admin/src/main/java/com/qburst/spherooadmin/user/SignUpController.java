package com.qburst.spherooadmin.user;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class SignUpController {
    private UserService userService;
    @PostMapping("/registration")
    public ResponseEntity<String> registration(@Valid @RequestBody Users users ){
        if(userService.emailVerification(users.getEmailId())) {
            return ResponseEntity.badRequest().body("email exists");
        }
        else {
            userService.createNewUser(users);
            return ResponseEntity.ok("succes");
        }
    }

}
