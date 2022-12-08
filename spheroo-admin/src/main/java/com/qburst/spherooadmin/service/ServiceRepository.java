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
    void updateService(String serviceName, String description, Boolean variablePrice, List<ServiceCharge> serviceChargeList, Long id);

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

    /**
     * the method checks if a {@link Service} of given name exists.
     * @param serviceName
     * @return true if a Service of name exists, otherwise false.
     */

    public boolean existsByServiceName(String serviceName);

    /**
     * Search a Service using service name.
     * @param serviceName
     * @return {@link Service} for given service name.
     */

    public Service findByServiceName(String serviceName);
    @Query(nativeQuery = true,value = "SELECT category_id FROM service WHERE service_id=?1")
    public long findCategoryIdFromServiceId(long serviceId);

    /**
     * method for getting a list of service name for a given category ID value as a content of {@link Page}
     * The names are listed in ascending order.
     * @param categoryId
     * @param pageable contain data for page number and page size.
     * @return {@link Page}
     */
    @Query(value = "SELECT s.service_name FROM service s WHERE s.category_id=?1 ORDER BY s.service_name",nativeQuery = true)
    Page<String> findServiceNameForACategory(long categoryId, Pageable pageable);

    /**
     * method find service name for given service id.
     * @param id of {@link Service}
     * @return {@link Service} name.
     */
    @Query(value = "select serviceName from Service where serviceId=?1")
    String findServiceNameByServiceId(long id);
}
