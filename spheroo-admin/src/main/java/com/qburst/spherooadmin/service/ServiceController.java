package com.qburst.spherooadmin.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * Controller for Service
 */
@RestController
@AllArgsConstructor
@RequestMapping("/service")
public class ServiceController {

    private ServiceService serviceService;

    @GetMapping("/id={id}")
    ResponseEntity<Optional<Service>> getById(@PathVariable long id) {
        return new ResponseEntity<>(serviceService.getServiceById(id), HttpStatus.OK);
    }

    @PutMapping("/id={id}")
    ResponseEntity<HttpStatus> updateService(@RequestBody Service service, @PathVariable long id) {
        serviceService.updateServiceById(service.getServiceName(), service.getDescription(), service.getVariablePrice(), service.getServiceChargeList(), id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/id={id}")
    ResponseEntity<HttpStatus> deleteService(@PathVariable long id) {
        serviceService.deleteServiceById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/page={page}&qty={noOfElements}&id={id}")
    ResponseEntity<Page<Service>> getServicesByCategoryId(@PathVariable long id, @PathVariable int page, @PathVariable int noOfElements) {
        return new ResponseEntity<>(serviceService.getServiceByCategoryId(id, page, noOfElements), HttpStatus.OK);
    }

    @GetMapping("/category_id={id}")
    ResponseEntity<List<String>> getServiceNamesByCategoryId(@PathVariable long id) {
        return new ResponseEntity<>(serviceService.getServiceNameByCategoryId(id), HttpStatus.OK);
    }
}
