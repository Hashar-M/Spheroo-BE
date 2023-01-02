package com.qburst.spherooadmin.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author Akhilesh
 * An interface extending {@link org.springframework.data.jpa.repository.JpaRepository} for {@link com.qburst.spherooadmin.user} model.
 */
public interface UsersRepository extends JpaRepository<Users,Long>
{
    /**
     * Update the last login field for the user by its id
     * @param lastLogin The new login date
     * @param userId The id of the user
     */
    @Transactional
    @Modifying
    @Query("update Users u set u.lastLogin = ?1 where u.userId = ?2")
    void updateLastLoginByUserId(Date lastLogin, long userId);
    /**
     * {@code boolean existsByEmailId(String email)}
     * Method for verify the existance of a {@link com.qburst.spherooadmin.user} with a given email.
     * @param email
     * @return true if user with given email exists; else false.
     */
    boolean existsByEmailId(String email);
    Users findByEmailId(String email);

    /**
     * method for updating password value of a {@link org.springframework.security.core.userdetails.User}
     * @param password new password value
     * @param emailId of the {@link Users}
     */
    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET password=?1 WHERE email_id=?2 ;",nativeQuery = true)
    public void changePassword(String password,String emailId);
}
