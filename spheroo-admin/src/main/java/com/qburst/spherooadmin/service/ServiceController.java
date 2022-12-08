package com.qburst.spherooadmin.service;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.qburst.spherooadmin.signup.ResponseDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
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
    private ModelMapper modelMapper;

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
     * Update a single service given in {@link ServicePutDTO}
     * @param servicePutDTO contain data for updating {@link Service}
     * @return
     */
    @PutMapping("/update")
    ResponseEntity<HttpStatus> updateService(@Valid @RequestBody ServicePutDTO servicePutDTO) {
        Service service=modelMapper.map(servicePutDTO,Service.class);
        serviceEntityService.updateASingleService(service);
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

    /**
     * This method allows you to import a CSV file straight into the database provided that the
     * file is in the correct format.
     * This was made in order to quickly enter in test data so is not meant to be used in production.
     * @param multipartFile The CSV file to enter into the Database.
     * @return The HTTP status OK.
     */
    @PostMapping("/import")
    public ResponseEntity<HttpStatus> importServicesFromCSV(@RequestParam("file") MultipartFile multipartFile) {
        CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
        CsvMapper mapper = new CsvMapper();
        List<Service> serviceList;
        try {
            MappingIterator<Service> readValues = mapper.readerFor(Service.class).with(bootstrapSchema).readValues(multipartFile.getInputStream());
            serviceList = readValues.readAll();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        serviceEntityService.saveListOfServices(serviceList);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
