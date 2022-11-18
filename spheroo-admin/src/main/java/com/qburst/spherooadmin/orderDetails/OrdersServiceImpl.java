package com.qburst.spherooadmin.orderDetails;

import com.qburst.spherooadmin.category.CategoryRepository;
import com.qburst.spherooadmin.service.ServiceChargeRepository;
import com.qburst.spherooadmin.service.ServiceRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class OrdersServiceImpl implements OrdersService {

    private OrdersRepository ordersRepo;
    private CategoryRepository categoryRepository;
    private ServiceRepository serviceRepository;
    private ServiceChargeRepository serviceChargeRepository;

    @Override
    public Orders getOrderById(long id) {
        return ordersRepo.getReferenceById(id);
    }

    @Override
    public void addOrder(Orders order) {
        ordersRepo.save(order);
    }

    @Override
    public Page<OrdersDisplayDTO> getAllOrdersPaged(int pageNo, int noOfElements,String columnToSort,boolean isAsc,String status) {
        Pageable pageWithRequiredElements;
        if(isAsc) {
            pageWithRequiredElements = PageRequest.of(pageNo,noOfElements, Sort.by(columnToSort));
        } else {
            pageWithRequiredElements = PageRequest.of(pageNo,noOfElements, Sort.by(columnToSort).descending());
        }
        Page<Orders> ordersPage;
        if(status.equalsIgnoreCase("open")) {
            ordersPage = ordersRepo.findByOpenOrderStatus(pageWithRequiredElements);
        } else if (status.equalsIgnoreCase("closed")) {
            ordersPage = ordersRepo.findByClosedOrderStatus(pageWithRequiredElements);
        } else if (status.equalsIgnoreCase("overdue")) {
            // 2 days (48 hrs) from due date considered as overdue
            ordersPage = ordersRepo.getOrderByDuePeriod("48 HOURS",pageWithRequiredElements);
        } else {
            //4 days (96 hours) from due date considered as Escalation
            ordersPage = ordersRepo.getOrderByDuePeriod("96 HOURS",pageWithRequiredElements);
        }
        List<OrdersDisplayDTO> ordersDisplayDTOList = new ArrayList<>();
        for (Orders order: ordersPage) {
            OrdersDisplayDTO ordersDisplayDTO = new OrdersDisplayDTO();
            ordersDisplayDTO.setOrderId(order.getOrderId());
            ordersDisplayDTO.setCustomerName(order.getCustomerName());
            ordersDisplayDTO.setCreatedDate(order.getCreatedDate());
            ordersDisplayDTO.setDeliveryFromDate(order.getDeliveryFromDate());
            ordersDisplayDTO.setDeliveryToDate(order.getDeliveryToDate());
            ordersDisplayDTO.setComments(order.getComments());
            ordersDisplayDTO.setZipCode(order.getZipCode());
            ordersDisplayDTO.setOrderStatus(order.getOrderStatus());
            ordersDisplayDTO.setCategoryName(categoryRepository.getReferenceById(order.getCategoryId()).getCategoryName());
            ordersDisplayDTO.setServiceName(serviceRepository.getReferenceById(order.getServiceId()).getServiceName());
            ordersDisplayDTO.setCharge(serviceChargeRepository.findChargeByPriority(order.getServiceId(), "NORMAL"));
            ordersDisplayDTO.setAssignedSupplier("Not available");
            ordersDisplayDTOList.add(ordersDisplayDTO);
        }
        Page<OrdersDisplayDTO> ordersDisplayDTOPage = new PageImpl<>(ordersDisplayDTOList,pageWithRequiredElements,ordersDisplayDTOList.size());
        return ordersDisplayDTOPage;
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
