package com.qburst.spherooadmin.orderDetails;
import com.qburst.spherooadmin.search.OrderFilter;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrdersService {

    OrdersDisplayDTO getOrderById(long id);
    void addOrder(Orders order);
    Page<OrdersDisplayDTO> getAllOrdersPaged(int pageNo, int noOfElements,String columnToSort,boolean isAsc, String status);
    String updateOrdersById(AmendOrderDTO amendOrderDTO);
    boolean deleteOrderById(long Id);
    OrderStatisticsDTO getOrdersStatistics();
    Page<Orders> findAllOrdersBySpecification(OrderFilter orderFilter, int pageNo, int noOfElements);
    int assignOrder(AssignedOrder assignedOrder);
    void saveListOfOrders(List<Orders> ordersList);
}
