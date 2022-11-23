package com.qburst.spherooadmin.orderDetails;

import com.qburst.spherooadmin.category.CategoryRepository;
import com.qburst.spherooadmin.constants.OrdersConstants;
import com.qburst.spherooadmin.search.OrderFilter;
import com.qburst.spherooadmin.service.ServiceChargeRepository;
import com.qburst.spherooadmin.service.ServiceRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
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
    private AssignedOrderRepository assignedOrderRepository;

    @Override
    public OrdersDisplayDTO getOrderById(long id) {
        Orders orders = ordersRepo.getReferenceById(id);
        OrdersDisplayDTO ordersDisplayDTO= new OrdersDisplayDTO();
        ordersDisplayDTO.setOrderId(orders.getOrderId());
        ordersDisplayDTO.setCustomerName(orders.getCustomerName());
        ordersDisplayDTO.setCreatedDate(orders.getCreatedDate());
        ordersDisplayDTO.setDeliveryFromDate(orders.getDeliveryFromDate());
        ordersDisplayDTO.setDeliveryToDate(orders.getDeliveryToDate());
        ordersDisplayDTO.setComments(orders.getComments());
        ordersDisplayDTO.setZipCode(orders.getZipCode());
        ordersDisplayDTO.setOrderStatus(orders.getOrderStatus());
        ordersDisplayDTO.setCategoryId(orders.getCategoryId());
        ordersDisplayDTO.setCategoryName(categoryRepository.getReferenceById(orders.getCategoryId()).getCategoryName());
        ordersDisplayDTO.setServiceName(serviceRepository.getReferenceById(orders.getServiceId()).getServiceName());
        ordersDisplayDTO.setCharge(serviceChargeRepository.findChargeByPriority(orders.getServiceId(),"NORMAL"));
        ordersDisplayDTO.setIssueImagesList(orders.getImagesList());
        return ordersDisplayDTO;
    }

    @Override
    public void addOrder(Orders order) {
        ordersRepo.save(order);
    }

    @Override
    public boolean assignOrder(AssignedOrder assignedOrder) {
        if(ordersRepo.existsById(assignedOrder.getOrderId())){
            assignedOrderRepository.save(assignedOrder);
            Orders orders = ordersRepo.getReferenceById(assignedOrder.getOrderId());
            orders.setOrderStatus(OrderStatus.UNACCEPTED.toString());
            return true;
        } else{
            return false;
        }

    }

    /**
     * function for getting order details based on status in pageable format.
     * @param pageNo accepts page number.
     * @param noOfElements no of data required in single page
     * @param columnToSort accept column name to sort.
     * @param isAsc accept sorting direction.
     * @param status accept status for filtering.
     * @return orders details in the page format.
     */
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
            ordersPage = ordersRepo.getOrderByDuePeriod(OrdersConstants.OVERDUE_STARTING,pageWithRequiredElements);
        } else {
            //4 days (96 hours) from due date considered as Escalation
            ordersPage = ordersRepo.getOrderByDuePeriod(OrdersConstants.ESCALATIONS_STARTING,pageWithRequiredElements);
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
    public boolean updateOrdersById(AmendOrderDTO amendOrderDTO, long orderId) {
        if(ordersRepo.existsById(orderId)){
            Orders orders = ordersRepo.getReferenceById(orderId);
            orders.setDeliveryFromDate(amendOrderDTO.getDeliveryFromDate());
            orders.setDeliveryToDate(amendOrderDTO.getDeliveryToDate());
            ordersRepo.save(orders);
            return true;
        }else {
            return false;
        }
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
        orderStatisticsDTO.setOpenOrdersGreenFlag(ordersRepo.getOpenOrderCountByDuePeriod(OrdersConstants.GREEN_FLAG_STARTING,OrdersConstants.BLUE_FLAG_STARTING));
        orderStatisticsDTO.setOpenOrdersBlueFlag(ordersRepo.getOpenOrderCountByDuePeriod(OrdersConstants.BLUE_FLAG_STARTING,OrdersConstants.RED_FLAG_STARTING));
        orderStatisticsDTO.setOpenOrdersRedFlag(ordersRepo.getOpenOrderCountByDuePeriod(OrdersConstants.RED_FLAG_STARTING,OrdersConstants.OVERDUE_STARTING));
        //ongoing order
        orderStatisticsDTO.setOngoingOrdersCount(ordersRepo.findOngoingOrderStatusCount());
        orderStatisticsDTO.setOngoingOrdersGreenFlag(ordersRepo.getOngoingOrderCountByDuePeriod(OrdersConstants.GREEN_FLAG_STARTING,OrdersConstants.BLUE_FLAG_STARTING));
        orderStatisticsDTO.setOngoingOrdersBlueFlag(ordersRepo.getOngoingOrderCountByDuePeriod(OrdersConstants.BLUE_FLAG_STARTING,OrdersConstants.RED_FLAG_STARTING));
        orderStatisticsDTO.setOngoingOrdersRedFlag(ordersRepo.getOngoingOrderCountByDuePeriod(OrdersConstants.RED_FLAG_STARTING,OrdersConstants.OVERDUE_STARTING));
        //overdue order
        orderStatisticsDTO.setOverdueOrdersCount(ordersRepo.findOverdueOrderStatusCount(OrdersConstants.OVERDUE_STARTING,OrdersConstants.ESCALATIONS_STARTING));
        orderStatisticsDTO.setOverdueOrdersUnassigned(ordersRepo.findOverdueUnassignedCount(OrdersConstants.OVERDUE_STARTING,OrdersConstants.ESCALATIONS_STARTING));
        orderStatisticsDTO.setOverdueOrdersUnaccepted(ordersRepo.findOverdueUnacceptedCount(OrdersConstants.OVERDUE_STARTING,OrdersConstants.ESCALATIONS_STARTING));
        //escalations
        orderStatisticsDTO.setEscalationsCount(ordersRepo.getOrdersCountByDuePeriod(OrdersConstants.ESCALATIONS_STARTING));
        return orderStatisticsDTO;
    }

    /**
     * Finds all orders in the database based on the specified criteria
     * @param orderFilter The specification to selects the orders on
     * @param pageNo The page number
     * @param noOfElements The number of elements to return at a time
     * @return A page object which contains orders based on the specified criteria
     */
    @Override
    public Page<Orders> findAllOrdersBySpecification(OrderFilter orderFilter, int pageNo, int noOfElements) {
        Pageable pageable = PageRequest.of(pageNo, noOfElements);
        return ordersRepo.findAll(orderFilter, pageable);
    }
}
