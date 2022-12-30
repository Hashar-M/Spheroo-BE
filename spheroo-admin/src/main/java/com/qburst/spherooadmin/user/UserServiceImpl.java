package com.qburst.spherooadmin.user;

import com.qburst.spherooadmin.constants.EmailConstants;
import com.qburst.spherooadmin.constants.ResponseConstants;
import com.qburst.spherooadmin.email.EmailService;
import com.qburst.spherooadmin.exception.ResetTokenExpiredException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.OffsetDateTime;
import java.util.Date;
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

    /**
     * Generate a password reset request for a user and sends a password reset email to them
     * @param emailId Email of the user to send the request to
     */
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
            emailService.sendForgotPasswordMail(emailId,
                    EmailConstants.EMAIL_BODY + EmailConstants.PASSWORD_RESET_PATH + passwordResetToken.getToken(),
                    passwordResetToken.getToken());
        }
    }

    /**
     * Reset the password for the user according to the reset token provided
     * @param token The password reset token
     * @param password The new password of the user
     */
    @Override
    public void resetPassword(String token, String password) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findPasswordResetTokenByToken(token);
        if(passwordResetToken != null) {
            OffsetDateTime currentTime = OffsetDateTime.now();
            if (!currentTime.isAfter(passwordResetToken.getExpiryDate())) {
                String email = passwordResetToken.getUser().getEmailId();
                changePassword(email, password);
            }
            else {
                throw new ResetTokenExpiredException(ResponseConstants.PASSWORD_RESET_TOKEN_EXPIRED);
            }
        }
        else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public void setLastLoginForUser(long userId, Date lastLogin) {
        usersRepository.updateLastLoginByUserId(lastLogin, userId);
    }

}
