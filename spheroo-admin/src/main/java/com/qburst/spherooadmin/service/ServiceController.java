package com.qburst.spherooadmin.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/service")
public class ServiceController {

    ServiceService serviceService;
    @GetMapping("/manage_categories")
    public ResponseEntity<?> getManageCategoryDetails (@RequestParam int page,@RequestParam int noOfElements){
        return ResponseEntity.status(HttpStatus.OK).body(serviceService.getManageCategoryDetails(page,noOfElements));
    }
}
