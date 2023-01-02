package com.qburst.spherooadmin.supplier;

import com.qburst.spherooadmin.search.SupplierPaginationFilter;
import com.qburst.spherooadmin.signup.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.Optional;

import static com.qburst.spherooadmin.constants.ResponseConstants.PAGINATION_PAGE_NUMBER;
import static com.qburst.spherooadmin.constants.ResponseConstants.PAGINATION_PAGE_SIZE;

@Validated
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

    /**
     * method return a {@link org.springframework.data.domain.Page} of Supplier details({@link SupplierGetDTO})
     * @param pageNo page number.
     * @param pageSize size for page.
     * @param key name of constraints for sorting.
     * @param asc sorting direction, ascending or descending
     * @param enabledSupplier sorting supplier by their visibility.
     * @return
     */
    @GetMapping("/get/list")
    public ResponseEntity<SupplierPageDTO> getSuppliersAsPage(@RequestParam(name = "page-no",defaultValue = "0") @Positive(message = PAGINATION_PAGE_NUMBER) int pageNo,
                                                              @RequestParam(name = "page-size",defaultValue = "6") @Positive(message = PAGINATION_PAGE_SIZE) int pageSize,
                                                              @RequestParam(name = "key") SupplierPagingConstraint key,
                                                              @RequestParam(name = "asc",defaultValue = "true") Boolean asc,
                                                              @RequestParam(name = "enabledSupplier",required = false) Boolean enabledSupplier,
                                                              @RequestParam(name = "searchName") String searchName){

        SupplierPaginationFilter supplierPaginationFilter=new SupplierPaginationFilter();
        supplierPaginationFilter.setKey(key);
        supplierPaginationFilter.setAsc(asc);
        supplierPaginationFilter.setEnabledSupplier(enabledSupplier);
        supplierPaginationFilter.setSearchName(searchName);

        return ResponseEntity.ok(supplierService.getPageOfSupplier(pageNo-1,pageSize,supplierPaginationFilter));
    }

    /**
     * function for getting supplier details as default.
     * {@link Supplier} with visibility value true will be present in the list(Only enabled suppliers).
     * @param orderId accepts order id
     * @return returns list of supplier details related to given categories.
     */
    @GetMapping("/get-suppliers")
    public ResponseEntity<MatchedSuppliersGetDTO> getSupplierByCategoryIdAndZip(@RequestParam(name = "order-id") long orderId){
        MatchedSuppliersGetDTO matchedSuppliersGetDTO=new MatchedSuppliersGetDTO();
        matchedSuppliersGetDTO.setFilterSupplierForAssignDTOList(supplierService.getSuppliersToAssign(orderId));
        return new ResponseEntity<>(matchedSuppliersGetDTO,HttpStatus.OK);
    }

    /**
     * deletes a supplier of given {@link Supplier} name
     * @param supplierName name of supplier to delete.
     * @return {@link ResponseDTO} with a true boolean value for successful deletion.
     */
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteASupplier(@RequestParam(name = "supplier-name") String supplierName){
        ResponseDTO responseDTO=new ResponseDTO();
        if(supplierService.deleteSupplierFromSupplierName(supplierName)){
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
     *It gives a {@link MatchedSuppliersGetDTO} has a content of {@link List} of {@link FilterSupplierForAssignDTO} based on the filtering parameter.
     * {@link Supplier} with visibility value true will be present in the list(Only enabled suppliers).
     * @param categoryName for {@link Supplier}
     * @param pinCode of {@link Supplier}
     * @param rating for the {@link Supplier }
     * @return {@link MatchedSuppliersGetDTO}
     */
    @GetMapping("/get-suppliers/filter")
    public ResponseEntity<MatchedSuppliersGetDTO> supplerFilteringForACategory(
                                              @RequestParam(name = "category-name") String categoryName,
                                              @RequestParam(name = "pin-code",defaultValue = "") String pinCode,
                                              @RequestParam(name = "rating",defaultValue = "1")@Min(value = 1,message = "rating should be greater than or equal one")
                                                                                               @Max(value = 5,message = "rating should be less than or equal five") int rating){
        List<FilterSupplierForAssignDTO> list=supplierService.filteredListOfSupplierForACategoryId(categoryName,pinCode,rating);
        MatchedSuppliersGetDTO matchedSuppliersGetDTO=new MatchedSuppliersGetDTO();
        matchedSuppliersGetDTO.setFilterSupplierForAssignDTOList(list);
        return new ResponseEntity<>(matchedSuppliersGetDTO,HttpStatus.OK);
    }

    /**
     * method for enable and disable a {@link Supplier}.
     * @param supplierId id value of supplier
     * @param action, a boolean value.A true value is for enabling a {@link Supplier} and a false boolean for disabling the supplier.
     * @return HTTP status CREATED for successful update of visibility of supplier.
     */

    @PutMapping("/visibility")
    public ResponseEntity<ResponseDTO> manageVisibilityOfSupplier(@PositiveOrZero @RequestParam(name = "supplier-id") long supplierId,
                                                                  @RequestParam(name = "enable") boolean action){
        ResponseDTO responseDTO=supplierService.alterVisibilityOfSupplier(supplierId,action);
        if(responseDTO.isSuccess()){
            return new ResponseEntity<>(responseDTO,HttpStatus.OK);
        }
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }
}
