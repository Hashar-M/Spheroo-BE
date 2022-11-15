package com.qburst.spherooadmin.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ServiceRepository extends JpaRepository<Service,Long> {

    @Query(value = "SELECT service_id FROM service WHERE category_id IS NULL",nativeQuery = true)
    List<Long> findNullCategoryServices();

    @Query(value = "SELECT category.category_id, category.category_name , COUNT(service.service_id) AS No_of_services FROM service\n" +
            "LEFT JOIN category ON service.category_id =category.category_id\n" +
            "GROUP BY category.category_name, category.category_id\n" +
            "ORDER BY category.category_name",nativeQuery = true)
    List<?> getServiceCountGroupByCategory();
}
