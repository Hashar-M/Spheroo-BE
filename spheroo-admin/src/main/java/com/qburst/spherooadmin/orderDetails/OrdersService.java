package com.qburst.spherooadmin.orderDetails;
import com.qburst.spherooadmin.search.OrderFilter;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface OrdersService {

    OrdersDisplayDTO getOrderById(long id);
    void addOrder(Orders order);
    Page<OrdersDisplayDTO> getAllOrdersPaged(int pageNo, int noOfElements,String columnToSort,boolean isAsc, String status);
    void updateOrdersById(AmendOrderDTO amendOrderDTO);
    void deleteOrderById(long Id);
    OrderStatisticsDTO getOrdersStatistics();
    OrdersDisplayDTOPage findAllOrdersBySpecification(OrderFilter orderFilter, int pageNo, int noOfElements);
    void assignOrder(AssignedOrder assignedOrder);
    RejectReasonsDTO getRejectReasons();
    void rejectOrder(long orderId, long reasonId);
    void uploadImage(MultipartFile imageFile, long orderId);

    /**
     * Saves multiple orders into the database from a list
     * @param ordersList the list of services to save into the database
     */
    void saveListOfOrders(List<Orders> ordersList);

    /**
     * create zip file of images and converted into byte array for the given order.
     * @param orderId
     * @param zipFileSavingFileLocation
     * @param zipFileNamePrefix
     * @param zipFileNameSuffix
     * @return
     */
    byte[] createZipImageFileForTheOrder(long orderId,String zipFileSavingFileLocation,String zipFileNamePrefix,String zipFileNameSuffix);
}
