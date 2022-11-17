package com.qburst.spherooadmin.supplier;

import com.qburst.spherooadmin.category.CategoryRepository;
import com.qburst.spherooadmin.supplieruser.SupplierUserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public List<SupplierGetDTO> getAListOfSupplier(int pageNo,int pageSize){
        Pageable pageable= PageRequest.of(pageNo,pageSize);
        Page<Supplier> suppliersPage=supplierRepository.findAll(pageable);
        List<Supplier> suppliers=new ArrayList<>();
        if(suppliersPage!=null && suppliersPage.hasContent()){
            suppliers=suppliersPage.getContent();
            List<SupplierGetDTO> supplierGetDTOS=new ArrayList<>();
            suppliers.forEach(supplier -> {
                SupplierGetDTO supplierGetDTO=new SupplierGetDTO();
                supplierGetDTO.setSupplierId(supplier.getSupplierId());
                supplierGetDTO.setSupplierName(supplier.getSupplierName());
                supplierGetDTO.setCategory(supplier.getCategoryNames());
                supplierGetDTO.setPinCode(supplier.getSupplierAddress().getPinCode());
                supplier.getSupplierUsers().forEach(supplierUser -> {
                    if(supplierUser.getSupplierUserType()== SupplierUserType.MANAGER){
                        supplierGetDTO.setContactName(supplierUser.getName());
                        supplierGetDTO.setContactNumber(supplierUser.getMobileNumber());
                        supplierGetDTO.setEmailId(supplierUser.getSupplierUserEmail());
                    }
                });
            supplierGetDTOS.add(supplierGetDTO);
            });
        return supplierGetDTOS;
        }
        List<SupplierGetDTO> supplierGetDTOS=new ArrayList<>();
        return supplierGetDTOS;
    }
@Transactional
    public boolean deleteSupplierFromSupplierName(String supplierName){
        if(supplierRepository.existsBySupplierName(supplierName)){
            long supplierId=supplierRepository.getSupplierIdFromSupplierName(supplierName);
            supplierRepository.deleteById(supplierId);
            //supplierRepository.deleteBySupplierName(supplierName);
            return true;
        }
        return false;
    }
    public Optional<Supplier> getTheSupplier(String supplierName){
        if(supplierRepository.existsBySupplierName(supplierName)){
            long supplierId=supplierRepository.getSupplierIdFromSupplierName(supplierName);
            Optional<Supplier> supplier=supplierRepository.findById(supplierId);
            return supplier;
        }
        return Optional.empty();
    }

    public boolean editTheSupplier(Supplier supplier){
        if(supplier.getSupplierId()>=0 && supplierRepository.existsById(supplier.getSupplierId())){
            supplierRepository.save(supplier);
            return true;
        }
        return false;
    }


}
