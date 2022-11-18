package com.qburst.spherooadmin.supplieruser;

import com.qburst.spherooadmin.supplieruser.SupplierUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for managing {@link SupplierUser} entity.
 */
@Repository
public interface SupplierUserRepository extends JpaRepository<SupplierUser,Integer> {
}
