package com.qburst.spherooadmin.supplier;

import com.qburst.spherooadmin.category.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class SupplierServiceImp implements SupplierService {
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    @Transactional
    public void addSupplier(Supplier supplier) throws EntityNotFoundException{
        String categoryName=supplier.getCategoryNames();
        if (categoryRepository.existsByCategoryName(categoryName)){
            supplier.setCategoryId(categoryRepository.getCategoryIdFromCategoryName(categoryName));
            supplier.getSupplierUsers().forEach(supplierUser -> supplierUser.setSupplier(supplier));
            supplierRepository.save(supplier);
        }
        else{
             throw new EntityNotFoundException();
        }
    }

}
