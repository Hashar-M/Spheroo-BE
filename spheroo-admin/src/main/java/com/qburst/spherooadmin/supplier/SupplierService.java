package com.qburst.spherooadmin.supplier;

import java.util.List;
import java.util.Optional;

public interface SupplierService {
    public void addSupplier(Supplier supplier);
    public List<SupplierGetDTO> getAListOfSupplier(int pageNo,int pageSize);
    public boolean deleteSupplierFromSupplierName(String supplierName);
    public Optional<Supplier> getTheSupplier(String supplierName);
    public boolean editTheSupplier(Supplier supplier);
}
