package com.qburst.spherooadmin.user;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public boolean emailVerification(String email){
        return usersRepository.existsByEmailId(email);
    }
    public void createNewUser(Users users){
        users.setUserRole("admin");
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        usersRepository.save(users);
    }
}
