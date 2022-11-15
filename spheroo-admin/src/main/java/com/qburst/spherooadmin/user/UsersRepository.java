package com.qburst.spherooadmin.user;

import org.springframework.data.jpa.repository.JpaRepository;

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
}
