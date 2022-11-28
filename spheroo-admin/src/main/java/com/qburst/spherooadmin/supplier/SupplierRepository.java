package com.qburst.spherooadmin.supplier;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier,Long> {
/*
    @Query(nativeQuery = true,value = "SELECT category_id FROM supplier WHERE supplier_id=?1")
    int getSupplier(int categoryId);
*/
}
