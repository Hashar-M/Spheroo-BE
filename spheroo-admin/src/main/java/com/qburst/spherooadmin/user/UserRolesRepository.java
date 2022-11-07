package com.qburst.spherooadmin.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRolesRepository extends JpaRepository<UserRoles, Long>
{
    UserRoles findByUserRole(String userRole);
}
