package com.qburst.spherooadmin.orderDetails;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Objects;

/**
 * The repository for Orders entity.
 */
public interface OrdersRepository extends JpaRepository<Orders,Long>, JpaSpecificationExecutor<Orders> {

    /**
     * to get all orders by order status.
     * @param orderStatus describes the status value.
     * @return list of orders filtered by status.
     */
    List<Orders> findByOrderStatus(String orderStatus);

    /**
     * to get orders in the form of pageable which matching the date difference from due date.
     * @param duePeriodInHours date difference value in hours.
     * @param pageable giving the pagination criteria.
     * @return return orders in the form of page.
     */
    @Query(value = "SELECT new com.qburst.spherooadmin.orderDetails.OrdersDisplayDTO(ord.orderId, ord.customerName, ord.createdDate, ord.deliveryFromDate, ord.deliveryToDate, ord.comments, ord.zipCode, ord.orderStatus, ord.categoryId, ord.serviceId, cat.categoryName, ser.serviceName, ord.isAmended)  " +
            "FROM Orders ord " +
            "INNER JOIN Category cat ON (ord.categoryId = cat.categoryId)" +
            " INNER JOIN Service ser ON ord.serviceId = ser.serviceId " +
            " where ord.orderStatus in ('UNASSIGNED','UNACCEPTED','ACCEPTED') and Extract(epoch from(current_timestamp-ord.deliveryToDate))/(60*60) >= ?1")
    Page<OrdersDisplayDTO> getOrderByDuePeriod(int duePeriodInHours, Pageable pageable);
    @Query(value = "SELECT new com.qburst.spherooadmin.orderDetails.OrdersDisplayDTO(ord.orderId, ord.customerName, ord.createdDate, ord.deliveryFromDate, ord.deliveryToDate, ord.comments, ord.zipCode, ord.orderStatus, ord.categoryId, ord.serviceId, cat.categoryName, ser.serviceName, ord.isAmended)  " +
            "FROM Orders ord " +
            "INNER JOIN Category cat ON (ord.categoryId = cat.categoryId)" +
            " INNER JOIN Service ser ON ord.serviceId = ser.serviceId ")
    Page<OrdersDisplayDTO> getOrdersByFilter(Specification<Orders> spec, Pageable pageable);

    /**
     * function for selecting orders which are included in open order category.
     * @param pageable giving the pagination criteria.
     * @return return orders in the form of page.
     */
    @Query(value = "SELECT new com.qburst.spherooadmin.orderDetails.OrdersDisplayDTO(ord.orderId, ord.customerName, ord.createdDate, ord.deliveryFromDate, ord.deliveryToDate, ord.comments, ord.zipCode, ord.orderStatus, ord.categoryId, ord.serviceId, cat.categoryName, ser.serviceName, ord.isAmended)  " +
            "FROM Orders ord " +
            "INNER JOIN Category cat ON (ord.categoryId = cat.categoryId)" +
            " INNER JOIN Service ser ON ord.serviceId = ser.serviceId " +
            " where ord.orderStatus in ('UNASSIGNED','UNACCEPTED')")
    Page<OrdersDisplayDTO> findByOpenOrderStatus(Pageable pageable);

    /**
     * function for selecting orders which are included in ongoing order category.
     * @param pageable giving the pagination criteria.
     * @return return orders in the form of page.
     */
    @Query(value = "SELECT new com.qburst.spherooadmin.orderDetails.OrdersDisplayDTO(ord.orderId, ord.customerName, ord.createdDate, ord.deliveryFromDate, ord.deliveryToDate, ord.comments, ord.zipCode, ord.orderStatus, ord.categoryId, ord.serviceId, cat.categoryName, ser.serviceName, ord.isAmended)  " +
            "FROM Orders ord " +
            "INNER JOIN Category cat ON (ord.categoryId = cat.categoryId)" +
            " INNER JOIN Service ser ON ord.serviceId = ser.serviceId " +
            " where ord.orderStatus in ('ACCEPTED')")
    Page<OrdersDisplayDTO> findByOngoingOrderStatus(Pageable pageable);
    /**
     * function for getting open order count.
     * @return count of open orders in int data type.
     */
    @Query(value = "SELECT COUNT(order_id) AS open_order_count from orders WHERE order_status IN ('UNASSIGNED','UNACCEPTED')",nativeQuery = true)
    int findOpenOrderStatusCount();

    /**
     * function for getting UNASSIGNED order count.
     * @return count of UNASSIGNED orders in int data type.
     */
    @Query(value = "SELECT COUNT(order_id) AS unassigned_order_count from orders WHERE order_status IN ('UNASSIGNED')",nativeQuery = true)
    int findUnassignedOrderStatusCount();

    /**
     * function for getting UNACCEPTED order count.
     * @return count of UNACCEPTED orders in int data type.
     */
    @Query(value = "SELECT COUNT(order_id) AS unaccepted_order_count from orders WHERE order_status IN ('UNACCEPTED')",nativeQuery = true)
    int findUnacceptedOrderStatusCount();

    /**
     * function for getting count of open orders with given due date period.
     * @param dueStartPeriodInHours due date minimum period.
     * @param dueEndPeriodInHours due date maximum period.
     * @return count of open orders with given due date period in int format.
     */
    @Query(value = "SELECT COUNT(order_id) FROM orders WHERE (delivery_to_date <= NOW() - CAST(?1 AS INTERVAL) AND (delivery_to_date > NOW() - CAST(?2 AS INTERVAL)) AND order_status IN ('UNASSIGNED','UNACCEPTED'))",nativeQuery = true)
    int getOpenOrderCountByDuePeriod(String dueStartPeriodInHours, String dueEndPeriodInHours);

    /**
     * function for getting ongoing order count (status as ACCEPTED)
     * @return count of ongoing order in int format.
     */
    @Query(value = "SELECT COUNT(order_id) AS ongoing_order_count from orders WHERE order_status IN ('ACCEPTED')",nativeQuery = true)
    int findOngoingOrderStatusCount();
    @Query(value = "SELECT COUNT(order_id) FROM orders WHERE (delivery_to_date <= NOW() - CAST(?1 AS INTERVAL) AND (delivery_to_date > NOW() - CAST(?2 AS INTERVAL)) AND order_status IN ('ACCEPTED'))",nativeQuery = true)
    int getOngoingOrderCountByDuePeriod(String dueStartPeriodInHours, String dueEndPeriodInHours);

    /**
     *function for getting count of overdue orders (oder status ACCEPTED/ UNASSIGNED/ UNACCEPTED and due period from 2 hours to 4 hours
     * @return count of overdue orders in int format.
     */
    @Query(value = "SELECT COUNT(order_id) AS overdue_order_count from orders WHERE ( delivery_to_date <= NOW() - CAST(?1 AS INTERVAL) AND (delivery_to_date > NOW() - CAST(?2 AS INTERVAL))  AND order_status IN ('ACCEPTED','UNASSIGNED','UNACCEPTED'))",nativeQuery = true)
    int findOverdueOrderStatusCount(String noOfDueInHours, String escalationDueInHours);

    /**
     * function for getting count of UNASSIGNED overdue orders.
     * @param noOfDueInHours overdue min hours period.
     * @param escalationDueInHours escalation due period.
     * @return count of unassigned overdue orders in int format.
     */
    @Query(value = "SELECT COUNT(order_id) AS overdue_unassigned_order_count from orders WHERE ( delivery_to_date <= NOW() - CAST(?1 AS INTERVAL) AND (delivery_to_date > NOW() - CAST(?2 AS INTERVAL))  AND order_status IN ('UNASSIGNED'))",nativeQuery = true)
    int findOverdueUnassignedCount(String noOfDueInHours, String escalationDueInHours);

    /**
     * function for getting count of UNACCEPTED overdue orders.
     * @param noOfDueInHours overdue min hours period
     * @param escalationDueInHours escalation due period
     * @return count of unaccepted overdue orders.
     */
    @Query(value = "SELECT COUNT(order_id) AS overdue_unaccepted_order_count from orders WHERE ( delivery_to_date <= NOW() - CAST(?1 AS INTERVAL) AND (delivery_to_date > NOW() - CAST(?2 AS INTERVAL))  AND order_status IN ('UNACCEPTED'))",nativeQuery = true)
    int findOverdueUnacceptedCount(String noOfDueInHours, String escalationDueInHours);

    /**
     * function for getting count of ESCALATION orders.
     * @param duePeriodInHours due period for escalation
     * @return count of escalation orders in int format.
     */
    @Query(value = "SELECT COUNT(order_id) FROM orders WHERE (delivery_to_date <= NOW() - CAST(?1 AS INTERVAL) and order_status IN ('ACCEPTED','UNASSIGNED','UNACCEPTED'))",nativeQuery = true)
    int getOrdersCountByDuePeriod(String duePeriodInHours);
    /**
     * function for selecting orders which are included in closed order category
     * @param pageable giving the pagination criteria.
     * @return return orders in the form of page.
     */
    @Query(value = "SELECT new com.qburst.spherooadmin.orderDetails.OrdersDisplayDTO(ord.orderId, ord.customerName, ord.createdDate, ord.deliveryFromDate, ord.deliveryToDate, ord.comments, ord.zipCode, ord.orderStatus, ord.categoryId, ord.serviceId, cat.categoryName, ser.serviceName, ord.isAmended)  " +
            "FROM Orders ord " +
            "INNER JOIN Category cat ON (ord.categoryId = cat.categoryId)" +
            " INNER JOIN Service ser ON ord.serviceId = ser.serviceId " +
            " where ord.orderStatus in ('SERVICED','REJECTED')")
    Page<OrdersDisplayDTO> findByClosedOrderStatus(Pageable pageable);
}
