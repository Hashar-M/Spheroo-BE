package com.qburst.spherooadmin.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users,Long>
{
    boolean existsByEmailId(String email);
    Users findByEmailId(String email);
}
