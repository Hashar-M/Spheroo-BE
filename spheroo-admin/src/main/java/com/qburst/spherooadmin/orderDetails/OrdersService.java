package com.qburst.spherooadmin.orderDetails;

import org.springframework.data.domain.Page;

import java.util.List;

public interface OrdersService {

    Orders getOrderById(long id);
    List<Orders> getOrderByEscalation(int escalationPeriod);
    void addOrder(Orders order);
    Page<Orders> getAllOrdersPaged(int pageNo, int noOfElements,String columnToSort,boolean isAsc, String status);
    boolean updateOrdersById(Orders orders);
    boolean deleteOrderById(long Id);
    OrderStatisticsDTO getOrdersStatistics();
}
