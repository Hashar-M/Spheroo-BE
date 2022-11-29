package com.qburst.spherooadmin.service;

import com.qburst.spherooadmin.signup.ResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * Controller for Service Entity
 */
@RestController
@AllArgsConstructor
@RequestMapping("/service")
public class ServiceController {

    /**
     *  The Service for the Service Entity
     */
    private ServiceEntityService serviceEntityService;

    /**
     * Returns a service by id
     * @param id ID of the service to be returned
     * @return an Optional Object that expects a service
     */
    @GetMapping("/id={id}")
    ResponseEntity<Optional<Service>> getById(@PathVariable long id) {
        return new ResponseEntity<>(serviceEntityService.getServiceById(id), HttpStatus.OK);
    }

    /**
     * Update service by id
     * @param service Service entity
     * @param id of the service to update
     * @return HttpStatus OK if service was successfully updated
     */
    @PutMapping("/id={id}")
    ResponseEntity<HttpStatus> updateService(@RequestBody Service service, @PathVariable long id) {
        serviceEntityService.updateServiceById(service.getServiceName(), service.getDescription(), service.getVariablePrice(), service.getServiceChargeList(), id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Deletes a particular service for a category
     * @param id id of the service to be deleted
     * @return HttpStatus NO_CONTENT if service was successfully deleted
     */
    @DeleteMapping("/id={id}")
    ResponseEntity<HttpStatus> deleteService(@PathVariable long id) {
        serviceEntityService.deleteServiceById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Gets a page of services under a category
     * @param id of the category
     * @param page page number
     * @param noOfElements number of elements to return at a time
     * @return Page object along with HttpStatus OK
     */
    @GetMapping("/page={page}&qty={noOfElements}&id={id}")
    ResponseEntity<Page<Service>> getServicesByCategoryId(@PathVariable long id, @PathVariable int page, @PathVariable int noOfElements) {
        return new ResponseEntity<>(serviceEntityService.getServiceByCategoryId(id, page, noOfElements), HttpStatus.OK);
    }

    /**
     * Gets the name of services under a category
     * @param id of the category
     * @return a list of strings containing names of services under a category
     */
    @GetMapping("/category_id={id}")
    ResponseEntity<List<String>> getServiceNamesByCategoryId(@PathVariable long id) {
        return new ResponseEntity<>(serviceEntityService.getServiceNameByCategoryId(id), HttpStatus.OK);
    }

    /**
     * Gives all {@link Service} names for a given {@link com.qburst.spherooadmin.category.Category} name as a {@link Page}
     * @param categoryName
     * @param pageNumber
     * @param pageSize
     * @return {@link ResponseDTO} as body of response.
     */
    @GetMapping
    public ResponseEntity<?> serviceNamesForTheCategory(@RequestParam(name = "category_name",required = true) String categoryName,
                                                        @RequestParam(name = "page_no") int pageNumber,
                                                        @RequestParam(name = "page_size") int pageSize){
        ResponseDTO responseDTO;
        responseDTO=serviceEntityService.getListOfServiceNamesForTheGivenCategory(categoryName,pageNumber,pageSize);
        if(responseDTO.isSuccess()) {
            return new ResponseEntity<>(responseDTO, HttpStatus.FOUND);
        }
        /**
         * response for the category name that doesn't exists.
         */
        return new ResponseEntity<>(responseDTO,HttpStatus.NOT_FOUND);
    }
}
