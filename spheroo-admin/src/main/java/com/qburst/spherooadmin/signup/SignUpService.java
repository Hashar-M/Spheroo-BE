package com.qburst.spherooadmin.signup;

import com.qburst.spherooadmin.user.UserRole;
import com.qburst.spherooadmin.user.Users;
import com.qburst.spherooadmin.user.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author Akhilesh
 * Service class used for sign up operations.
 */
@Service
public class SignUpService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    /**
     * {@code public boolean emailVerification(String email)}
     * A method checks the existence of a user with email address.
     * @param email - email of user as String.
     * @return true if a user with given email id exists in {@link com.qburst.spherooadmin.user.UsersRepository};
     *  else false.
     */
    public boolean emailVerification(String email){
        return usersRepository.existsByEmailId(email);
    }

    /**
     * {@code public void createNewUser(Users users)}
     * This method create a new user.
     * @param users - takes input a new user.
     */
    public void createNewUser(Users users){
        users.setUserRole(UserRole.ADMIN);
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        usersRepository.save(users);
    }
}
