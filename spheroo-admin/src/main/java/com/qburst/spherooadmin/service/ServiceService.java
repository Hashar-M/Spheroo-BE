package com.qburst.spherooadmin.service;


import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ServiceService {
    Optional<Service> getServiceById(Long id);
    void saveService(Service service);
    void deleteServiceById(Long id);
    void updateServiceById(String serviceName, String description, String variablePrice, List<ServiceCharge> serviceChargeList, Long id);
    Page<Service> getServiceByCategoryId(Long category_id, int pageNo, int qty);
    List<String> getServiceNameByCategoryId(Long category_id);
}
