package com.qburst.spherooadmin.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
@AllArgsConstructor
public class ServiceServiceImpl implements ServiceService{

    ServiceRepository serviceRepository;
    @Override
    public List<?> getManageCategoryDetails(int pageNo, int noOfElements) {
        Pageable pageableCriteria = PageRequest.of(pageNo, noOfElements, Sort.by("categoryName"));
        log.info("data  ",serviceRepository.getServiceCountGroupByCategory());
        return serviceRepository.getServiceCountGroupByCategory();
    }
}
