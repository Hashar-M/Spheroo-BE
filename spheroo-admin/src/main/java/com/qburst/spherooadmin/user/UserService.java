package com.qburst.spherooadmin.user;

/**
 * interface for user related services.
 */
public interface UserService {
    /**
     * Method to check if the email is already in use
     * @param email the email to check
     * @return returns true if the email exists
     */
    boolean isEmailAlreadyInUse(String email);

    /**
     * Method to create a new user
     * @param users The user to create
     */
    void createNewUser(Users users);
    Users getUserByEmailId(String email);

    /**
     * method for changing password of a user.
     * @param emailId emailId of the user to change the password of
     * @param password the new password of the user
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
