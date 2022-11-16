package com.qburst.spherooadmin.supplieruser;

import com.qburst.spherooadmin.supplieruser.SupplierUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierUserRepository extends JpaRepository<SupplierUser,Integer> {
}
