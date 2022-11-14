package com.qburst.spherooadmin.supplier;

import com.qburst.spherooadmin.signup.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SupplierController {
    @Autowired
    private SupplierServiceImp supplierServiceImp;
    @PostMapping("/add/supplier")
    public ResponseEntity<String> addSupplier(@RequestBody Supplier supplier){
        supplierServiceImp.addSupplier(supplier);
        return ResponseEntity.ok("Ok");
    }
    @GetMapping("/get/supplier")
    public ResponseEntity<ResponseDTO> getSupplier(){
        return ResponseEntity.ok(new ResponseDTO());
    }
}
