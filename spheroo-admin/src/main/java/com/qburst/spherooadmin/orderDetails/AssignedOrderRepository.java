package com.qburst.spherooadmin.orderDetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AssignedOrderRepository extends JpaRepository<AssignedOrder,Long> {
    @Query(value = "SELECT COUNT(supplier_id) FROM assigned_order WHERE supplier_id = ?1",nativeQuery = true)
    int getAssignedOrderCount(long supplierId);
}
