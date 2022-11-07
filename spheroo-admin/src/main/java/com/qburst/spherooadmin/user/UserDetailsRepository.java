package com.qburst.spherooadmin.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsRepository extends JpaRepository<UserDetails,Long>
{
    UserDetails findByFirstName(String firstName);
}
