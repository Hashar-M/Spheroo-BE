package com.qburst.spherooadmin.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Akhilesh
 * An interface extending {@link org.springframework.data.jpa.repository.JpaRepository} for {@link com.qburst.spherooadmin.user} model.
 */
public interface UsersRepository extends JpaRepository<Users,Long>
{
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
