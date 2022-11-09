package com.qburst.spherooadmin.signup;

import com.qburst.spherooadmin.user.UserRole;
import com.qburst.spherooadmin.user.Users;
import com.qburst.spherooadmin.user.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SignUpService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public boolean emailVerification(String email){
        return usersRepository.existsByEmailId(email);
    }
    public void createNewUser(Users users){
        users.setUserRole(UserRole.ADMIN);
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        usersRepository.save(users);
    }
}
