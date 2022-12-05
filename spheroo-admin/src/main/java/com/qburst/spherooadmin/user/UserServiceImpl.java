package com.qburst.spherooadmin.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @inheritDoc
 * @author Akhilesh
 * Service class used for user specific operations.
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService{
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * {@code public boolean isEmailAlreadyInUse(String email)}
     * A method checks the existence of a user with email address.
     * @param email - email of user as String.
     * @return true if a user with given email id exists in {@link com.qburst.spherooadmin.user.UsersRepository};
     *  else false.
     */
    public boolean isEmailAlreadyInUse(String email){
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
    @Override
    public Users getUserByEmailId(String email) {
        return usersRepository.findByEmailId(email);
    }

    /**
     *method for changing password of a user.
     * @param emailId of curently login user, the {@link java.security.Principal}
     * @param password new value for password.
     */
    @Override
    public void changePassword(String emailId,String password){
        log.info(passwordEncoder.encode(password),emailId);
        usersRepository.changePassword(passwordEncoder.encode(password),emailId);
    }

}
