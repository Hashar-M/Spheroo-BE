package com.qburst.spherooadmin.service;


import com.qburst.spherooadmin.signup.ResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

/**
 * The service for the service entity
 */
public interface ServiceEntityService {

    /**
     * Returns a service by its ID.
     * @param id ID of the service to return
     * @return An optional object that can contain a service object.
     */
    Optional<Service> getServiceById(Long id);

    /**
     * Stores a new service into the database
     * @param service The service entity to store
     */
    void saveService(Service service);

    /**
     * Deletes a service based on its ID
     * @param id The id of the service to delete
     */
    void deleteServiceById(Long id);

    /**
     * Update an existing service according to its ID
     * @param id the id of the service to update
     */
    void updateServiceById(String serviceName, String description, Boolean variablePrice, List<ServiceCharge> serviceChargeList, Long id);

    /**
     * Returns a page of services for the specified category
     * @param category_id the id of the category to return services of
     * @param pageNo pageable object
     * @param qty Quantity of entities to return at a time
     * @return Page with the needed details
     */
    Page<Service> getServiceByCategoryId(Long category_id, int pageNo, int qty);

    /**
     * Gets the names of services under a category specified by its ID.
     * @param category_id The id of the category under whose services you want to get.
     * @return a List of strings containing the names of services under the category
     */
    List<String> getServiceNameByCategoryId(Long category_id);

    /**
     * fetch the data for service name along with the status of operation is added to the DTO {@link ResponseDTO}
     * @param categoryName
     * @param pageNo
     * @param pageSize
     * @return
     */
    public ResponseDTO getListOfServiceNamesForTheGivenCategory(String categoryName,int pageNo,int pageSize);

    void saveListOfServices(List<Service> serviceList);
}
