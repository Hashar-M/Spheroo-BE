package com.qburst.spherooadmin.orderDetails;

import com.qburst.spherooadmin.category.CategoryRepository;
import com.qburst.spherooadmin.constants.OrdersConstants;
import com.qburst.spherooadmin.search.OrderFilter;
import com.qburst.spherooadmin.service.ServiceChargeRepository;
import com.qburst.spherooadmin.service.ServiceRepository;
import com.qburst.spherooadmin.supplier.SupplierRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrdersServiceImpl implements OrdersService {

    private OrdersRepository ordersRepo;
    private CategoryRepository categoryRepository;
    private ServiceRepository serviceRepository;
    private ServiceChargeRepository serviceChargeRepository;
    private AssignedOrderRepository assignedOrderRepository;
    private SupplierRepository supplierRepository;
    private IssueImagesRepository issueImagesRepository;

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
        ordersDisplayDTO.setImagesList(orders.getImagesList());
        return ordersDisplayDTO;
    }

    @Override
    public void addOrder(Orders order) {
        ordersRepo.save(order);
    }

    @Override
    public int assignOrder(AssignedOrder assignedOrder) {
        if(ordersRepo.existsById(assignedOrder.getOrderId())){
            if(supplierRepository.existsById(assignedOrder.getSupplierId())){

                assignedOrderRepository.save(assignedOrder);
                Orders orders = ordersRepo.getReferenceById(assignedOrder.getOrderId());
                orders.setOrderStatus(OrderStatus.UNACCEPTED.toString());
                ordersRepo.save(orders);
                //return 0: denotes saved successfully
                return 0;
            }
            //return 1 : denotes supplier id doesn't exist
            return 1;
        } else if (supplierRepository.existsById(assignedOrder.getSupplierId())) {
            //return 2: denotes order id doesn't exist;
            return 2;
        }else {
            //return 3: denotes both order id and supplier id do not exist.
            return 3;
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
        Page<OrdersDisplayDTO> ordersDisplayDTOPageTest;
        ordersDisplayDTOPageTest =ordersRepo.getOrderByDuePeriod(OrdersConstants.OVERDUE_STARTING, pageWithRequiredElements);
        ordersDisplayDTOPageTest.forEach(order->{
            order.setCharge(serviceChargeRepository.findChargeByPriority(order.getServiceId(),"NORMAL"));
            order.setAssignedSupplier("Not Assigned");
            order.setImagesList(issueImagesRepository.findIssueImagesByOrderId(order.getOrderId()));
        });
//        Page<Orders> ordersPage;
//        if(status.equalsIgnoreCase("open")) {
//            ordersPage = ordersRepo.findByOpenOrderStatus(pageWithRequiredElements);
//        } else if (status.equalsIgnoreCase("closed")) {
//            ordersPage = ordersRepo.findByClosedOrderStatus(pageWithRequiredElements);
//        } else if (status.equalsIgnoreCase("overdue")) {
//            // 2 days (48 hrs) from due date considered as overdue
//            ordersPage = ordersRepo.getOrderByDuePeriod(OrdersConstants.OVERDUE_STARTING,pageWithRequiredElements);
//            return ordersDisplayDTOPageTest;
//        } else {
//            //4 days (96 hours) from due date considered as Escalation
//            ordersPage = ordersRepo.getOrderByDuePeriod(OrdersConstants.ESCALATIONS_STARTING,pageWithRequiredElements);
//        }
//        List<OrdersDisplayDTO> ordersDisplayDTOList = new ArrayList<>();
//        for (Orders order: ordersPage) {
//            OrdersDisplayDTO ordersDisplayDTO = new OrdersDisplayDTO();
//            ordersDisplayDTO.setOrderId(order.getOrderId());
//            ordersDisplayDTO.setCustomerName(order.getCustomerName());
//            ordersDisplayDTO.setCreatedDate(order.getCreatedDate());
//            ordersDisplayDTO.setDeliveryFromDate(order.getDeliveryFromDate());
//            ordersDisplayDTO.setDeliveryToDate(order.getDeliveryToDate());
//            ordersDisplayDTO.setComments(order.getComments());
//            ordersDisplayDTO.setZipCode(order.getZipCode());
//            ordersDisplayDTO.setOrderStatus(order.getOrderStatus());
//            ordersDisplayDTO.setCategoryName(categoryRepository.getReferenceById(order.getCategoryId()).getCategoryName());
//            ordersDisplayDTO.setServiceName(serviceRepository.getReferenceById(order.getServiceId()).getServiceName());
//            ordersDisplayDTO.setCharge(serviceChargeRepository.findChargeByPriority(order.getServiceId(), "NORMAL"));
//            ordersDisplayDTO.setAssignedSupplier("Not available");
//            ordersDisplayDTOList.add(ordersDisplayDTO);
//        }
//        Page<OrdersDisplayDTO> ordersDisplayDTOPage = new PageImpl<>(ordersDisplayDTOList,pageWithRequiredElements,ordersDisplayDTOList.size());
//        return ordersDisplayDTOPage;
        return ordersDisplayDTOPageTest;
    }

    @Override
    public boolean updateOrdersById(AmendOrderDTO amendOrderDTO) {
        if(ordersRepo.existsById(amendOrderDTO.getOrderId())){
            Orders orders = ordersRepo.getReferenceById(amendOrderDTO.getOrderId());
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
        OpenOrderStatistics openOrderStatistics = new OpenOrderStatistics();
        openOrderStatistics.setOrdersCount(ordersRepo.findOpenOrderStatusCount());
        openOrderStatistics.setOrdersUnassigned(ordersRepo.findUnassignedOrderStatusCount());
        openOrderStatistics.setOrdersUnaccepted(ordersRepo.findUnacceptedOrderStatusCount());
        openOrderStatistics.setOrdersGreenFlag(ordersRepo.getOpenOrderCountByDuePeriod(OrdersConstants.GREEN_FLAG_STARTING,OrdersConstants.BLUE_FLAG_STARTING));
        openOrderStatistics.setOrdersBlueFlag(ordersRepo.getOpenOrderCountByDuePeriod(OrdersConstants.BLUE_FLAG_STARTING,OrdersConstants.RED_FLAG_STARTING));
        openOrderStatistics.setOrdersRedFlag(ordersRepo.getOpenOrderCountByDuePeriod(OrdersConstants.RED_FLAG_STARTING,OrdersConstants.OVERDUE_STARTING));
        orderStatisticsDTO.setOpen(openOrderStatistics);
        //ongoing order
        OngoingOrderStatistics ongoingOrderStatistics = new OngoingOrderStatistics();
        ongoingOrderStatistics.setOrdersCount(ordersRepo.findOngoingOrderStatusCount());
        ongoingOrderStatistics.setOrdersGreenFlag(ordersRepo.getOngoingOrderCountByDuePeriod(OrdersConstants.GREEN_FLAG_STARTING,OrdersConstants.BLUE_FLAG_STARTING));
        ongoingOrderStatistics.setOrdersBlueFlag(ordersRepo.getOngoingOrderCountByDuePeriod(OrdersConstants.BLUE_FLAG_STARTING,OrdersConstants.RED_FLAG_STARTING));
        ongoingOrderStatistics.setOrdersRedFlag(ordersRepo.getOngoingOrderCountByDuePeriod(OrdersConstants.RED_FLAG_STARTING,OrdersConstants.OVERDUE_STARTING));
        orderStatisticsDTO.setOngoing(ongoingOrderStatistics);
        //overdue order
        OverdueOrderStatistics overdueOrderStatistics = new OverdueOrderStatistics();
        overdueOrderStatistics.setOrdersCount(ordersRepo.findOverdueOrderStatusCount(OrdersConstants.OVERDUE_STARTING,OrdersConstants.ESCALATIONS_STARTING));
        overdueOrderStatistics.setOrdersUnassigned(ordersRepo.findOverdueUnassignedCount(OrdersConstants.OVERDUE_STARTING,OrdersConstants.ESCALATIONS_STARTING));
        overdueOrderStatistics.setOrdersUnaccepted(ordersRepo.findOverdueUnacceptedCount(OrdersConstants.OVERDUE_STARTING,OrdersConstants.ESCALATIONS_STARTING));
        orderStatisticsDTO.setOverdue(overdueOrderStatistics);
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
