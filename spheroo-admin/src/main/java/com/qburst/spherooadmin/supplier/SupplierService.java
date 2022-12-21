package com.qburst.spherooadmin.supplier;

import com.qburst.spherooadmin.search.SupplierPaginationFilter;
import com.qburst.spherooadmin.signup.ResponseDTO;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

/**
 * interface, abstract the methods for supplier services.
 */

public interface SupplierService {
    public void addSupplier(SupplierAddDTO supplierAddDTO);
    public SupplierPageDTO getPageOfSupplier(int pageNo, int pageSize, SupplierPaginationFilter specification);
    public boolean deleteSupplierFromSupplierName(String supplierName);
    public Optional<Supplier> getTheSupplier(String supplierName);
    public boolean editTheSupplier(Supplier supplier);
    public List<SupplierToAssignDTO> getSuppliersToAssign(long orderId);

    /**
     * The method map the {@link Supplier} data  for the below filtering parameter into {@link FilterSupplierForAssignDTO}.
     * @param categoryName
     * @param pin
     * @param rating
     * @return
     */
    public List<FilterSupplierForAssignDTO> filteredListOfSupplierForACategoryId(String categoryName, String pin, int rating);

    public ResponseDTO alterVisibilityOfSupplier(long supplierId, boolean action);
}
