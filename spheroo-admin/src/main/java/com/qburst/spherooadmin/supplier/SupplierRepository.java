package com.qburst.spherooadmin.supplier;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
/**
import java.util.Optional;
Repository for managing {@link Supplier} entity.
 */
@Repository
public interface SupplierRepository extends JpaRepository<Supplier,Long> {
  /**
   *Method for checking the existance of a supplier by supplier name.
   * @param supplierName
   * @return true if a supplier of given name exists, otherwise false.
   */
  boolean existsBySupplierName(String supplierName);


  /**
   * Method that gives the id of supplier from supplier name.
   * @param supplierName
   * @return id of supplier.
   */
  @Query(nativeQuery = true,value = "SELECT supplier_id FROM supplier WHERE supplier_name=?1")
  long getSupplierIdFromSupplierName(String supplierName);
}
