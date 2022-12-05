package com.qburst.spherooadmin.user;

/**
 * interface for user related services.
 */
public interface UserService {
    public boolean isEmailAlreadyInUse(String email);
    public void createNewUser(Users users);
    public Users getUserByEmailId(String email);

    /**
     * method for changing password of a user.
     * @param emailId
     * @param password
     */
    public void changePassword(String emailId,String password);
}
