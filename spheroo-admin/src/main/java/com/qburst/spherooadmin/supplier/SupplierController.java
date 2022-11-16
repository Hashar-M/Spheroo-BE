package com.qburst.spherooadmin.supplier;

import com.qburst.spherooadmin.signup.ResponseDTO;
import com.qburst.spherooadmin.supplieruser.SupplierUser;
import com.qburst.spherooadmin.supplieruser.SuppliersUsersPostDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/supplier")
public class SupplierController {
    @Autowired
    private SupplierServiceImp supplierServiceImp;
    @PostMapping("/add")
    public ResponseEntity<String> addSupplier(@Valid @RequestBody SupplierPostDTO supplierPostDTO){
        SupplierAddress supplierAddress =new SupplierAddress();
        supplierAddress.setDistrict(supplierPostDTO.getSupplierAddressPostDTO().getDistrict());
        supplierAddress.setTown(supplierPostDTO.getSupplierAddressPostDTO().getTown());
        supplierAddress.setCountry(supplierPostDTO.getSupplierAddressPostDTO().getCountry());
        supplierAddress.setBuildNo(supplierPostDTO.getSupplierAddressPostDTO().getBuildNo());
        supplierAddress.setPinCode(supplierPostDTO.getSupplierAddressPostDTO().getPinCode());

        List<SuppliersUsersPostDTO> suppliersUsersPostDTOS=supplierPostDTO.getSuppliersUsersPostDTOS();
        List<SupplierUser> supplierUsers=new ArrayList<>();

        suppliersUsersPostDTOS.forEach(supplierUsersPostDTO->{
            SupplierUser supplierUser=new SupplierUser();
            supplierUser.setName(supplierUsersPostDTO.getSupplierUserName());
            supplierUser.setMobileNumber(supplierUsersPostDTO.getSupplierUserMobileNumber());
            supplierUser.setFixedLineNumber(supplierUsersPostDTO.getSupplierUserFixedMobileNumber());
            supplierUser.setSupplierUserEmail(supplierUsersPostDTO.getSupplierUserEmailId());
            supplierUser.setSupplierUserType(supplierUsersPostDTO.getSupplierUserType());
            supplierUsers.add(supplierUser);
        });

        Supplier supplier=new Supplier();

        supplier.setSupplierName(supplierPostDTO.getSupplierName());
        supplier.setTier(supplierPostDTO.getTier());
        supplier.setRating(supplierPostDTO.getRating());
        supplier.setCategoryNames(supplierPostDTO.getCategoryNames());
        supplier.setSupplierAddress(supplierAddress);
        supplier.setSupplierUsers(supplierUsers);

        supplierServiceImp.addSupplier(supplier);
        return ResponseEntity.ok("Ok");
    }
    @PostMapping("/")
    @GetMapping("/get/supplier")
    public ResponseEntity<ResponseDTO> getSupplier(){
        return ResponseEntity.ok(new ResponseDTO());
    }
}
