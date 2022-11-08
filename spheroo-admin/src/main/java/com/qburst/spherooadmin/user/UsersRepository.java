package com.qburst.spherooadmin.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users,Long>
{
    Users findByEmailId(String emailId);
    Users findByUserName(String userName);
}
