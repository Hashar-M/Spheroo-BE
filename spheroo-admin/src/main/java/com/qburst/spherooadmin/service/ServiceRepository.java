package com.qburst.spherooadmin.service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service,Long> {
    /**
     * function for getting list of service_id in which won't have any relation to any category.
     * @return list of service_id
     */
    @Query(value = "SELECT service_id FROM service WHERE category_id IS NULL",nativeQuery = true)
    List<Long> findNullCategoryServices();

}
