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
    public ResponseEntity<?> getSuppliersAsPage(@RequestParam(defaultValue = "0") int pageNo, @RequestParam(defaultValue = "1") int pageSize){
        ResponseDTO responseDTO=new ResponseDTO();
        if (pageSize<1){
            responseDTO.setSuccess(false);
            responseDTO.setMessage(" page size must be greater than 0 ");
            return ResponseEntity.ok(responseDTO);
        }
        return ResponseEntity.ok(supplierService.getPageOfSupplier(pageNo,pageSize));
    }

    /**
     * function for getting supplier details as default.
     * @param orderId accepts order id
     * @return returns list of supplier details related to given categories.
     */
    @GetMapping("/get-suppliers")
    public ResponseEntity<Object> getSupplierByCategoryIdAndZip(@RequestParam(name = "order-id") long orderId){
        MatchedSuppliersGetDTO matchedSuppliersGetDTO=new MatchedSuppliersGetDTO();
        matchedSuppliersGetDTO.setSupplierToAssignDTOList(supplierService.getSuppliersToAssign(orderId));
        return new ResponseEntity<>(matchedSuppliersGetDTO,HttpStatus.OK);
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

    /**
     *It gives a {@link org.springframework.data.domain.Page} has a content of {@link List} of {@link FilterSupplierForAssignDTO} based on the filtering parameter.
     * @param categoryId for {@link Supplier}
     * @param pinCode of {@link Supplier}
     * @param rating for the {@link Supplier }
     * @param pageNumber
     * @param pageSize
     * @return {@link org.springframework.data.domain.Page<FilterSupplierForAssignDTO>}
     */
    @GetMapping("/get-suppliers/filter")
    public ResponseEntity<?> supplerFilteringForACategory(@RequestParam(name = "category-id") long categoryId,
                                              @RequestParam(name = "pin-code") int pinCode,
                                              @RequestParam(name = "rating") int rating,
                                              @RequestParam(name = "page-no",defaultValue = "0") int pageNumber,
                                              @RequestParam(name = "page-size",defaultValue = "1") int pageSize){
        return new ResponseEntity<>(supplierService.filteredPageOfSupplierForACategoryId(categoryId,pinCode,rating,pageNumber,pageSize),HttpStatus.FOUND);
    }
}
