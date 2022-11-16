package com.qburst.spherooadmin.orderDetails;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class OrdersServiceImpl implements OrdersService {

    private OrdersRepository ordersRepo;

    @Override
    public Orders getOrderById(long id) {
        return ordersRepo.getReferenceById(id);
    }

    @Override
    public List<Orders> getOrderByEscalation(int escalationPeriod) {
        return null;
    }

    @Override
    public void addOrder(Orders order) {
        ordersRepo.save(order);
    }

    @Override
    public Page<Orders> getAllOrdersPaged(int pageNo, int noOfElements,String columnToSort,boolean isAsc,String status) {
        Pageable pageWithRequiredElements;
        if(isAsc) {
            pageWithRequiredElements = PageRequest.of(pageNo,noOfElements, Sort.by(columnToSort));
        } else {
            pageWithRequiredElements = PageRequest.of(pageNo,noOfElements, Sort.by(columnToSort).descending());
        }

        if(status.equalsIgnoreCase("open")) {
            return ordersRepo.findByOpenOrderStatus(pageWithRequiredElements);
        } else if (status.equalsIgnoreCase("closed")) {
            return ordersRepo.findByClosedOrderStatus(pageWithRequiredElements);
        } else if (status.equalsIgnoreCase("overdue")) {
            // 2 days (48 hrs) from due date considered as overdue
            return ordersRepo.getOrderByDuePeriod("48 HOURS",pageWithRequiredElements);
        } else {
            //4 days (96 hours) from due date considered as Escalation
            return ordersRepo.getOrderByDuePeriod("96 HOURS",pageWithRequiredElements);
        }
    }

    @Override
    public boolean updateOrdersById(Orders orders) {
        Orders existingOrder= ordersRepo.getReferenceById(orders.getOrderId());
        if(Objects.isNull(existingOrder)) {
            return false;
        }
        ordersRepo.save(orders);
        return true;
    }

    @Override
    public boolean deleteOrderById(long id) {
        boolean isExist= ordersRepo.existsById(id);
        if(isExist){
            ordersRepo.deleteById(id);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public OrderStatisticsDTO getOrdersStatistics() {
        //open order
        OrderStatisticsDTO orderStatisticsDTO = new OrderStatisticsDTO();
        orderStatisticsDTO.setOpenOrdersCount(ordersRepo.findOpenOrderStatusCount());
        orderStatisticsDTO.setOpenOrdersUnassigned(ordersRepo.findUnassignedOrderStatusCount());
        orderStatisticsDTO.setOpenOrdersUnaccepted(ordersRepo.findUnacceptedOrderStatusCount());
        orderStatisticsDTO.setOpenOrdersGreenFlag(ordersRepo.getOpenOrderCountByDuePeriod("0 HOURS","16 HOURS"));
        orderStatisticsDTO.setOpenOrdersBlueFlag(ordersRepo.getOpenOrderCountByDuePeriod("16 HOURS","32 HOURS"));
        orderStatisticsDTO.setOpenOrdersRedFlag(ordersRepo.getOpenOrderCountByDuePeriod("32 HOURS","48 HOURS"));
        //ongoing order
        orderStatisticsDTO.setOngoingOrdersCount(ordersRepo.findOngoingOrderStatusCount());
        orderStatisticsDTO.setOngoingOrdersGreenFlag(ordersRepo.getOngoingOrderCountByDuePeriod("0 HOURS","16 HOURS"));
        orderStatisticsDTO.setOngoingOrdersBlueFlag(ordersRepo.getOngoingOrderCountByDuePeriod("16 HOURS","32 HOURS"));
        orderStatisticsDTO.setOngoingOrdersRedFlag(ordersRepo.getOngoingOrderCountByDuePeriod("32 HOURS","48 HOURS"));
        //overdue order
        orderStatisticsDTO.setOverdueOrdersCount(ordersRepo.findOverdueOrderStatusCount("48 HOURS","96 HOURS"));
        orderStatisticsDTO.setOverdueOrdersUnassigned(ordersRepo.findOverdueUnassignedCount("48 HOURS","96 HOURS"));
        orderStatisticsDTO.setOverdueOrdersUnaccepted(ordersRepo.findOverdueUnacceptedCount("48 HOURS","96 HOURS"));
        //escalations
        orderStatisticsDTO.setEscalationsCount(ordersRepo.getOrdersCountByDuePeriod("96 HOURS"));
        return orderStatisticsDTO;
    }
}
