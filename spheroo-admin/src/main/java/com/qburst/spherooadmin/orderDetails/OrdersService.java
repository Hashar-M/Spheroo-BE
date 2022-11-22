package com.qburst.spherooadmin.orderDetails;
import org.springframework.data.domain.Page;

public interface OrdersService {

    OrdersDisplayDTO getOrderById(long id);
    void addOrder(Orders order);
    Page<OrdersDisplayDTO> getAllOrdersPaged(int pageNo, int noOfElements,String columnToSort,boolean isAsc, String status);
    boolean updateOrdersById(Orders orders);
    boolean deleteOrderById(long Id);
    OrderStatisticsDTO getOrdersStatistics();
    boolean assignOrder(AssignedOrder assignedOrder);
}
