package com.qburst.spherooadmin.supplier;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

/**
 * interface, abstract the methods for supplier services.
 */

public interface SupplierService {
    public void addSupplier(SupplierAddDTO supplierAddDTO);
    public SupplierPageDTO getPageOfSupplier(int pageNo, int pageSize);
    public boolean deleteSupplierFromSupplierName(String supplierName);
    public Optional<Supplier> getTheSupplier(String supplierName);
    public boolean editTheSupplier(Supplier supplier);
    public List<SupplierToAssignDTO> getSuppliersToAssign(long categoryId,long orderId,String zipcode);
}
