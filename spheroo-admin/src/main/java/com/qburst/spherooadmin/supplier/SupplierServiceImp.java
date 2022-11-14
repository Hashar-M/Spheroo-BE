package com.qburst.spherooadmin.supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImp implements SupplierService {
    @Autowired
    private SupplierRepository supplierRepository;
    @Override
    public void addSupplier(Supplier supplier){
      supplierRepository.save(supplier);
    }

}
