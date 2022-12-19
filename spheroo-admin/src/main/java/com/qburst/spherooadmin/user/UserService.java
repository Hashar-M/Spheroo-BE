package com.qburst.spherooadmin.user;

/**
 * interface for user related services.
 */
public interface UserService {
    boolean isEmailAlreadyInUse(String email);
    void createNewUser(Users users);
    Users getUserByEmailId(String email);

    /**
     * method for changing password of a user.
     * @param emailId
     * @param password
     */
    void changePassword(String emailId,String password);

    /**
     * Generate a password reset request for a user and sends a password reset email to them
     * @param emailId Email of the user to send the request to
     */
    void generateResetPasswordRequest(String emailId);

    /**
     * Reset the password for the user according to the reset token provided
     * @param token The password reset token
     * @param password The new password of the user
     */
    void resetPassword(String token, String password);
}
