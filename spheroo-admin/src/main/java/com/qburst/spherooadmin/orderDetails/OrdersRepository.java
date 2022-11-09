package com.qburst.spherooadmin.orderDetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders,Long>
{
    List<Orders> findByOrderStatus(String orderStatus);
    @Query(value = "SELECT * FROM orders WHERE (created_date <= NOW() - CAST(':escalation_period days' AS INTERVAL) and order_status != 'OPEN') or (created_date <= NOW() - CAST('2 days' AS INTERVAL) and order_status != 'OPEN' and service_priority='URGENT')",nativeQuery = true)
    List<Orders> getOrderByEscalation(@Param("escalation_period") int escalationPeriod);
    //check status closed or not,  priority


}
