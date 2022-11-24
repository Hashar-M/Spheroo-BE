package com.qburst.spherooadmin.orderDetails;
import org.springframework.data.domain.Page;

public interface OrdersService {

    Orders getOrderById(long id);
    void addOrder(Orders order);
    Page<OrdersDisplayDTO> getAllOrdersPaged(int pageNo, int noOfElements,String columnToSort,boolean isAsc, String status);
    boolean updateOrdersById(AmendOrderDTO amendOrderDTO);
    boolean deleteOrderById(long Id);
    OrderStatisticsDTO getOrdersStatistics();
}
