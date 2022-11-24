package com.qburst.spherooadmin.supplier;

import com.qburst.spherooadmin.signup.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/supplier")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;
    @PostMapping("/add")
    public ResponseEntity<ResponseDTO> addSupplier(@Valid @RequestBody SupplierAddDTO supplierAddDTO){
        supplierService.addSupplier(supplierAddDTO);
        ResponseDTO responseDTO=new ResponseDTO();
        responseDTO.setSuccess(true);
        return ResponseEntity.ok(responseDTO);
    }
    @GetMapping("/get/list")
    public ResponseEntity<SupplierPagingDTO> getSuppliersAsAList(@RequestParam int pageNo, @RequestParam int pageSize){
        List<SupplierGetDTO> supplierGetDTOS=supplierService.getAListOfSupplier(pageNo,pageSize);
        SupplierPagingDTO supplierPagingDTO=new SupplierPagingDTO();
        supplierPagingDTO.setSupplierGetDTO(supplierGetDTOS);
        return ResponseEntity.ok(supplierPagingDTO);
    }
    @GetMapping("/get-suppliers")
    public ResponseEntity<?> getSupplierByCategoryIdAndZip(@RequestParam long categoryId,@RequestParam long orderId,@RequestParam String zipcode){
        return ResponseEntity.status(HttpStatus.OK).body(supplierService.getSuppliersToAssign(categoryId,orderId,zipcode));
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
