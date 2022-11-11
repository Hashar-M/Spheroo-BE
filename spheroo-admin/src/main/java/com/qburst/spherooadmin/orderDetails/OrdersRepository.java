package com.qburst.spherooadmin.orderDetails;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
/**
 * The repository for Orders entity.
 */

public interface OrdersRepository extends JpaRepository<Orders,Long>
{
    /**
     * to get all orders by order status.
     * @param orderStatus describes the status value.
     * @return list of orders filtered by status.
     */
    List<Orders> findByOrderStatus(String orderStatus);

    /**
     * to get orders in the form of pageable which matching the date difference from due date.
     * @param escalationPeriod date difference value in days
     * @param pageable giving the pagination criteria
     * @return return orders in the form of page
     */
    @Query(value = "SELECT * FROM orders WHERE (delivery_to_date <= NOW() - CAST('?1 days' AS INTERVAL) and order_status IN ('UNASSIGNED','UNACCEPTED'))",nativeQuery = true)
    Page<Orders> getOrderByDuePeriod(@Param("escalation_period") int escalationPeriod, Pageable pageable);
    //check status closed or not,  priority

    /**
     * function for selecting orders which are included in open order category
     * @param pageable giving the pagination criteria
     * @return return orders in the form of page
     */
    @Query(value = "SELECT * from orders WHERE order_status IN ('UNASSIGNED','UNACCEPTED')",nativeQuery = true)
    Page<Orders> findByOpenOrderStatus(Pageable pageable);

    /**
     * function for selecting orders which are included in closed order category
     * @param pageable giving the pagination criteria.
     * @return return orders in the form of page.
     */
    @Query(value = "SELECT * from orders WHERE order_status IN ('ACCEPTED','SERVICED','REJECTED')",nativeQuery = true)
    Page<Orders> findByClosedOrderStatus(Pageable pageable);



}
