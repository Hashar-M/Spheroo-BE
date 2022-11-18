package com.qburst.spherooadmin.service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service,Long> {

    /**
     * Updates an existing service with the specified id
     */
    @Transactional
    @Modifying
    @Query("update Service s set s.serviceName = ?1, s.description = ?2, s.variablePrice = ?3, s.serviceChargeList = ?4 where s.serviceId = ?5")
    void updateService(String serviceName, String description, String variablePrice, List<ServiceCharge> serviceChargeList, Long id);

    /**
     * Gets all services under a category
     * @param category_id ID of the category of services to return
     * @param pageable pageable object that contains pageNo and noOfElements to return from a page
     * @return A page object with the required data
     */
    @Query(value = "SELECT * FROM service WHERE category_id = ?1", nativeQuery = true)
    Page<Service> getServicesByCategoryId(Long category_id, Pageable pageable);

    /**
     * Gets the list of service names under a category
     * @param id id of the category to return the name of
     * @return List of strings
     */
    @Query(value = "SELECT service_name FROM service WHERE category_id = ?1", nativeQuery = true)
    List<String> getServiceNameByCategoryId(Long id);

    void deleteByServiceId(Long id);

    /**
     * function for getting list of service_id in which won't have any relation to any category.
     * @return list of service_id
     */
    @Query(value = "SELECT service_id FROM service WHERE category_id IS NULL",nativeQuery = true)
    List<Long> findNullCategoryServices();

}
