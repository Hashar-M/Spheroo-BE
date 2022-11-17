package com.qburst.spherooadmin.supplier;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier,Long> {
  boolean existsBySupplierName(String supplierName);
/*  @Modifying
  @Query(nativeQuery = true,value = "DELETE FROM supplier WHERE supplier_name= :name")
  void deleteBySupplierName(@Param("name") String supplierName);*/
@Query(nativeQuery = true,value = "SELECT supplier_id FROM supplier WHERE supplier_name=?1")
  long getSupplierIdFromSupplierName(String supplierName);
}
