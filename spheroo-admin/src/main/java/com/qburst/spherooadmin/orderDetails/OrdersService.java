package com.qburst.spherooadmin.orderDetails;
import com.qburst.spherooadmin.search.OrderFilter;
import org.springframework.data.domain.Page;

public interface OrdersService {

    Orders getOrderById(long id);
    void addOrder(Orders order);
    Page<OrdersDisplayDTO> getAllOrdersPaged(int pageNo, int noOfElements,String columnToSort,boolean isAsc, String status);
    boolean updateOrdersById(Orders orders);
    boolean deleteOrderById(long Id);
    OrderStatisticsDTO getOrdersStatistics();
    Page<Orders> findAllOrdersBySpecification(OrderFilter orderFilter, int pageNo, int noOfElements);
}
