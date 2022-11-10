package com.qburst.spherooadmin.user;

/**
 * interface for user related services.
 */
public interface UserService {
    public boolean isEmailAlreadyInUse(String email);
    public void createNewUser(Users users);
}
