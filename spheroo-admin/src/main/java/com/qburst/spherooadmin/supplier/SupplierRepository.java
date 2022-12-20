package com.qburst.spherooadmin.supplier;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
  @Query(value = "SELECT * FROM supplier WHERE category_id =?1 and pin_code=?2 and visibility=true",nativeQuery = true)
  List<Supplier> findByCategoryId(long categoryId,String pinCode);

  /**
   * using jpa projection the result set for the query is mapped to {@link FilterSupplierForAssignDTO} .
   * @param categoryName for the {@link Supplier}
   * @param rating of the {@link Supplier}
   * @param pinCode for the {@link Supplier}
   * @return
   */
  @Query("select new com.qburst.spherooadmin.supplier.FilterSupplierForAssignDTO(s.supplierId,s.supplierName,s.rating) from Supplier s where s.categoryNames=:categoryName and s.rating>=:rating and s.supplierAddress.pinCode like %:pinCode% and s.visibility=true")
  List<FilterSupplierForAssignDTO> findAllOrderBySupplierName(@Param("categoryName") String  categoryName,@Param("rating") int rating,@Param("pinCode") String pinCode);

  @Query(value = "select s.visibility from Supplier s where s.supplierId=:supplierid")
  public boolean findVisibilityById(@Param(value = "supplierid") long supplierId);

  @Modifying
  @Transactional
  @Query(value = "update Supplier s set s.visibility=:value where s.supplierId=:supplierid")
  public void updateVisibilityById(@Param(value = "value") boolean value,@Param(value = "supplierid") long supplierId);
}
