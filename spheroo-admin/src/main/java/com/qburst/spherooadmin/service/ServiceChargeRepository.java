package com.qburst.spherooadmin.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ServiceChargeRepository extends JpaRepository<ServiceCharge,Long> {
    /**
     * function for getting all charge_id as list in which it won't have any reference to any service.
     * @return list of charge_id
     */
    @Query(value = "SELECT charge_id FROM service_charge WHERE service_id IS NULL",nativeQuery = true)
    List<Long> findNullServiceCharges();
}
