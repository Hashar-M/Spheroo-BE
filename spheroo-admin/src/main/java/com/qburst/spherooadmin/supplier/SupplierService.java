package com.qburst.spherooadmin.supplier;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

/**
 * interface, abstract the methods for supplier services.
 */

public interface SupplierService {
    public void addSupplier(SupplierAddDTO supplierAddDTO);
    public List<SupplierGetDTO> getAListOfSupplier(int pageNo,int pageSize);
    public boolean deleteSupplierFromSupplierName(String supplierName);
    public Optional<Supplier> getTheSupplier(String supplierName);
    public boolean editTheSupplier(Supplier supplier);
    public List<SupplierToAssignDTO> getSuppliersToAssign(long categoryId,long orderId,String zipcode);

    /**
     * The method map the {@link Supplier} data  for the below filtering parameter into {@link FilterSupplierForAssignDTO}.
     * @param categoryId
     * @param pin
     * @param rating
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public Page<FilterSupplierForAssignDTO> filteredPageOfSupplierForACategoryId(long categoryId, int pin, int rating, int pageNumber, int pageSize);
}
