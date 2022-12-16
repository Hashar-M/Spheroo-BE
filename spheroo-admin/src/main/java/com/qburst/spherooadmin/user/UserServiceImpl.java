package com.qburst.spherooadmin.user;

import com.qburst.spherooadmin.constants.EmailConstants;
import com.qburst.spherooadmin.email.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * @inheritDoc
 * @author Akhilesh
 * Service class used for user specific operations.
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{
    private UsersRepository usersRepository;
    private PasswordEncoder passwordEncoder;
    private PasswordResetTokenRepository passwordResetTokenRepository;
    private EmailService emailService;

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
        usersRepository.changePassword(passwordEncoder.encode(password),emailId);
    }

    @Override
    public void generateResetPasswordRequest(String emailId) {
        Users user = usersRepository.findByEmailId(emailId);
        if (user != null) {
            OffsetDateTime expirytime = OffsetDateTime.now().plusHours(EmailConstants.EXPIRY_TIME);
            PasswordResetToken passwordResetToken = new PasswordResetToken();
            passwordResetToken.setToken(UUID.randomUUID().toString());
            passwordResetToken.setUser(user);
            passwordResetToken.setExpiryDate(expirytime);
            passwordResetTokenRepository.save(passwordResetToken);
            emailService.sendForgotPasswordMail(emailId, "Test", passwordResetToken.getToken());
        }
    }

    @Override
    public void resetPassword(String token, String password) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findPasswordResetTokenByToken(token);
        OffsetDateTime currentTime = OffsetDateTime.now();
        if (!currentTime.isAfter(passwordResetToken.getExpiryDate())) {
            String email = passwordResetTokenRepository.findUsersByToken(token).getEmailId();
            changePassword(email, password);
        }
    }

}
