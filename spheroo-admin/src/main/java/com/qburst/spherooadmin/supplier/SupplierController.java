package com.qburst.spherooadmin.supplier;

import com.qburst.spherooadmin.signup.ResponseDTO;
import com.qburst.spherooadmin.supplieruser.SupplierUser;
import com.qburst.spherooadmin.supplieruser.SuppliersUsersPostDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/supplier")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;
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

        supplierService.addSupplier(supplier);
        return ResponseEntity.ok("Ok");
    }
    @GetMapping("/get/list")
    public ResponseEntity<SupplierPagingDTO> getSuppliersAsAList(@RequestParam int pageNo, @RequestParam int pageSize){
        List<SupplierGetDTO> supplierGetDTOS=supplierService.getAListOfSupplier(pageNo,pageSize);
        SupplierPagingDTO supplierPagingDTO=new SupplierPagingDTO();
        supplierPagingDTO.setSupplierGetDTO(supplierGetDTOS);
        return ResponseEntity.ok(supplierPagingDTO);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteASupplier(@RequestBody SupplierDeleteDTO supplierDeleteDTO){
        ResponseDTO responseDTO=new ResponseDTO();
        if(supplierService.deleteSupplierFromSupplierName(supplierDeleteDTO.getSupplierName())){
            responseDTO.setSuccess(true);
            return ResponseEntity.ok(responseDTO);
        }
        responseDTO.setSuccess(false);
        responseDTO.setMessage("supplier not found");
        return ResponseEntity.ok(responseDTO);
    }
    @GetMapping("/get")
    public ResponseEntity<Supplier> getSupplier(@RequestParam String supplierName){
        Optional<Supplier> supplier=supplierService.getTheSupplier(supplierName);
        if (supplier.isPresent()){
            Supplier supplier1=supplier.get();
            System.out.println(supplier1);
            return ResponseEntity.ok(supplier1);
        }
        return ResponseEntity.ok(null);
    }
    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateSupplier(@RequestBody Supplier supplier){
        ResponseDTO responseDTO=new ResponseDTO();
        responseDTO.setSuccess(supplierService.editTheSupplier(supplier));
        return ResponseEntity.ok(responseDTO);
    }
}
