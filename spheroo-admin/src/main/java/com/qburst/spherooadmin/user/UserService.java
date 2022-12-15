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

    void generateResetPasswordRequest(String emailId);

    void resetPassword(String token, String password);
}
