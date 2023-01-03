package com.qburst.spherooadmin.orderDetails;

import com.qburst.spherooadmin.category.CategoryRepository;
import com.qburst.spherooadmin.constants.OrdersConstants;
import com.qburst.spherooadmin.exception.WrongDataForActionException;
import com.qburst.spherooadmin.search.OrderFilter;
import com.qburst.spherooadmin.service.ServiceChargeRepository;
import com.qburst.spherooadmin.service.ServiceRepository;
import com.qburst.spherooadmin.supplier.SupplierRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.ZipFile;
import org.apache.commons.io.FileUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class OrdersServiceImpl implements OrdersService {

    private OrdersRepository ordersRepo;
    private CategoryRepository categoryRepository;
    private ServiceRepository serviceRepository;
    private ServiceChargeRepository serviceChargeRepository;
    private AssignedOrderRepository assignedOrderRepository;
    private SupplierRepository supplierRepository;
    private IssueImagesRepository issueImagesRepository;
    private RejectReasonRepository rejectReasonRepository;

    @Override
    public OrdersDisplayDTO getOrderById(long id) {
        if(!ordersRepo.existsById(id)){
            throw new EntityNotFoundException("No order exist with given data");
        }
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
        ordersDisplayDTO.setServiceId(orders.getServiceId());
        ordersDisplayDTO.setCategoryName(categoryRepository.getReferenceById(orders.getCategoryId()).getCategoryName());
        ordersDisplayDTO.setServiceName(serviceRepository.getReferenceById(orders.getServiceId()).getServiceName());
        String supplier = assignedOrderRepository.findSupplierByOrderId(ordersDisplayDTO.getOrderId());
        ordersDisplayDTO.setAssignedSupplier((supplier != null)? supplier: "Not assigned");
        ordersDisplayDTO.setCharge(serviceChargeRepository.findChargeByPriority(orders.getServiceId(),"NORMAL"));
        ordersDisplayDTO.setImagesList(orders.getImagesList());
        ordersDisplayDTO.setAmended(orders.isAmended());
        String reason = null;
        if(orders.getReasonId()!=0){
            reason = rejectReasonRepository.getReferenceById(orders.getReasonId()).getReason();
        }
        ordersDisplayDTO.setRejectReason(reason);
        return ordersDisplayDTO;
    }

    @Override
    @Transactional
    public void rejectOrder(long orderId, long reasonId) {
        if(!ordersRepo.existsById(orderId)){
            throw new EntityNotFoundException("No order exist with given data");
        }
        if(!rejectReasonRepository.existsById(reasonId)){
            throw new EntityNotFoundException("No reason exist with given data");
        }
        Orders orders = ordersRepo.getReferenceById(orderId);
        if(!orders.getOrderStatus().equalsIgnoreCase(OrderStatus.UNASSIGNED.toString())){
            throw new WrongDataForActionException("Only unassigned orders can reject");
        }
        orders.setOrderStatus(OrderStatus.REJECTED.toString());
        orders.setReasonId(reasonId);
        ordersRepo.save(orders);
    }

    @Override
    public RejectReasonsDTO getRejectReasons() {
        return new RejectReasonsDTO(rejectReasonRepository.findAll());
    }

    @Override
    @Transactional
    public void addOrder(Orders order) {
        ordersRepo.save(order);
    }

    @Override
    @Transactional
    public void assignOrder(AssignedOrder assignedOrder) {
        if(ordersRepo.existsById(assignedOrder.getOrderId())){
            if(supplierRepository.existsById(assignedOrder.getSupplierId())){
                Orders orders = ordersRepo.getReferenceById(assignedOrder.getOrderId());
                if(!orders.getOrderStatus().equalsIgnoreCase(OrderStatus.UNASSIGNED.toString())){
                    throw new WrongDataForActionException("Only unassigned orders can assign");
                }
                assignedOrderRepository.save(assignedOrder);
                orders.setOrderStatus(OrderStatus.UNACCEPTED.toString());
                ordersRepo.save(orders);
            }else{
                throw new EntityNotFoundException("Supplier not exist with given id");
            }
        } else if (supplierRepository.existsById(assignedOrder.getSupplierId())) {
            throw new EntityNotFoundException("Order not exist with given id");
        }else {
            throw new EntityNotFoundException("Both order id and supplier id do not exist");
        }
    }

    /**
     * Saves multiple orders into the database from a list
     * @param ordersList the list of services to save into the database
     */
    @Override
    @Transactional
    public void saveListOfOrders(List<Orders> ordersList) {
        ordersRepo.saveAll(ordersList);
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
        Page<OrdersDisplayDTO> ordersDisplayDTOPage;
        if(status.equalsIgnoreCase("open")) {
            ordersDisplayDTOPage = ordersRepo.findByOpenOrderStatus(pageWithRequiredElements);
        } else if (status.equalsIgnoreCase("closed")) {
            ordersDisplayDTOPage = ordersRepo.findByClosedOrderStatus(pageWithRequiredElements);
        } else if (status.equalsIgnoreCase("overdue")) {
            // 2 days (48 hrs) from due date considered as overdue
            ordersDisplayDTOPage = ordersRepo.getOrderByDuePeriod(OrdersConstants.OVERDUE_STARTING_INT,pageWithRequiredElements);
        }else if (status.equalsIgnoreCase("ongoing"))
        {
            //order status as ACCEPTED is considered as ongoing orders.
            ordersDisplayDTOPage = ordersRepo.findByOngoingOrderStatus(pageWithRequiredElements);
        }else {
            //4 days (96 hours) from due date considered as Escalation
            ordersDisplayDTOPage = ordersRepo.getOrderByDuePeriod(OrdersConstants.ESCALATIONS_STARTING_INT,pageWithRequiredElements);
        }
        ordersDisplayDTOPage.forEach(order-> {
            order.setCharge(serviceChargeRepository.findChargeByPriority(order.getServiceId(),"NORMAL"));
            String supplier = assignedOrderRepository.findSupplierByOrderId(order.getOrderId());
            order.setAssignedSupplier((supplier != null)? supplier: "Not assigned");
            order.setImagesList(issueImagesRepository.findIssueImagesByOrderId(order.getOrderId()));
        });
        return ordersDisplayDTOPage;
    }

    /**
     * function for updating existing order details.
     * @param amendOrderDTO accepts data for updating order.
     * @return
     */
    @Override
    @Transactional
    public void updateOrdersById(AmendOrderDTO amendOrderDTO) {
        if(ordersRepo.existsById(amendOrderDTO.getOrderId())){
            Orders orders = ordersRepo.getReferenceById(amendOrderDTO.getOrderId());
            //checking is the order amended once.
            if(orders.isAmended())
            {
                throw new WrongDataForActionException("this order already amended once, only single time amend is allowed");
            }
            //checking is order status UNASSIGNED or not.
            if(!orders.getOrderStatus().equalsIgnoreCase("UNASSIGNED")){
                throw new WrongDataForActionException("only unassigned orders can amend.");
            }
            Date date = new Date();
            // amend allowed till 8 hr before the date.
            if(!((orders.getDeliveryToDate().getTime()-date.getTime())>(8*60*60*1000))){
                throw new WrongDataForActionException("Amend allowed time exceeded.");
            }
            //updating date occur before previous date.
            if(orders.getDeliveryToDate().compareTo(amendOrderDTO.getDeliveryToDate())>0 || orders.getDeliveryFromDate().compareTo(amendOrderDTO.getDeliveryFromDate())>0){
                throw new WrongDataForActionException("updating date occur before original date.");
            }
            //checking 48 hr limit between original dae and new date.
            if(Math.abs(orders.getDeliveryFromDate().getTime()-amendOrderDTO.getDeliveryFromDate().getTime())>(48*60*60*1000)||Math.abs(orders.getDeliveryToDate().getTime()-amendOrderDTO.getDeliveryToDate().getTime())>(48*60*60*1000)){
                throw new WrongDataForActionException("48 hr limit exceeded.");
            }
            //checking any change in any date.
            if(orders.getDeliveryToDate().compareTo(amendOrderDTO.getDeliveryToDate())==0){
                throw new WrongDataForActionException("To date should be amended");
            }
            //checking is the given date before current date.
            if(date.compareTo(amendOrderDTO.getDeliveryToDate())>0){
                throw new WrongDataForActionException("please enter coming date for To date");
            }
            //checking time gap between new From date and new To date.
            if(amendOrderDTO.getDeliveryToDate().getTime()-amendOrderDTO.getDeliveryFromDate().getTime()<(24*60*60*1000)){
                throw new WrongDataForActionException("Time gap between From date and To date should be at least 24 hr");
            }
            orders.setDeliveryFromDate(amendOrderDTO.getDeliveryFromDate());
            orders.setDeliveryToDate(amendOrderDTO.getDeliveryToDate());
            orders.setAmended(true);
            ordersRepo.save(orders);
        }else {
            throw new EntityNotFoundException("No order exist with given data");
        }
    }

    /**
     * function for uploading image to file system and to store its path in the database.
     * @param imageFile accept image file as multipart file.
     * @param orderId accepts order id.
     */
    @Override
    @Transactional
    public void uploadImage(MultipartFile imageFile,long orderId) {
        if(!ordersRepo.existsById(orderId)){
            throw new WrongDataForActionException("No order exist with given data");
        }
        try {
            Path imagePath = Paths.get(OrdersConstants.IMAGE_FOLDER_PATH+"order_"+orderId+"/");
            if(!Files.exists(imagePath)){
                Files.createDirectories(imagePath);
            }
            InputStream inputStream = imageFile.getInputStream();
            Path filePath = imagePath.resolve(imageFile.getOriginalFilename());
            if(!Files.exists(filePath)){
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                Orders orders =ordersRepo.getReferenceById(orderId);
                orders.getImagesList().add(IssueImages.builder().issueImages(filePath.toString()).build());
                ordersRepo.save(orders);
            }

        }catch (IOException ex){
            throw new RuntimeException("Could not save the file");
        }
    }

    @Override
    @Transactional
    public void deleteOrderById(long id) {
        boolean isExist= ordersRepo.existsById(id);
        if(isExist){
            ordersRepo.deleteById(id);
        }else {
            throw new EntityNotFoundException("No order exist with given data");
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
    public OrdersDisplayDTOPage findAllOrdersBySpecification(OrderFilter orderFilter, int pageNo, int noOfElements) {
        Pageable pageable = PageRequest.of(pageNo, noOfElements);
        Page<Orders> ordersPage = ordersRepo.findAll(orderFilter,pageable);
        List<OrdersDisplayDTO> ordersDisplayDTOList = new ArrayList<>();
        ordersPage.forEach(orders -> {
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
            ordersDisplayDTO.setServiceId(orders.getServiceId());
            ordersDisplayDTO.setCategoryName(categoryRepository.getReferenceById(orders.getCategoryId()).getCategoryName());
            ordersDisplayDTO.setServiceName(serviceRepository.getReferenceById(orders.getServiceId()).getServiceName());
            String supplier = assignedOrderRepository.findSupplierByOrderId(ordersDisplayDTO.getOrderId());
            ordersDisplayDTO.setAssignedSupplier((supplier != null)? supplier: "Not assigned");
            ordersDisplayDTO.setCharge(serviceChargeRepository.findChargeByPriority(orders.getServiceId(),"NORMAL"));
            ordersDisplayDTO.setImagesList(orders.getImagesList());
            ordersDisplayDTO.setAmended(orders.isAmended());
            String reason = null;
            if(orders.getReasonId()!=0){
                reason = rejectReasonRepository.getReferenceById(orders.getReasonId()).getReason();
            }
            ordersDisplayDTO.setRejectReason(reason);
            ordersDisplayDTOList.add(ordersDisplayDTO);
        });
        OrdersDisplayDTOPage ordersDisplayDTOPage = new OrdersDisplayDTOPage();
        ordersDisplayDTOPage.setContent(ordersDisplayDTOList);
        ordersDisplayDTOPage.setPageNumber(ordersPage.getNumber());
        ordersDisplayDTOPage.setPageSize(ordersPage.getSize());
        ordersDisplayDTOPage.setTotalPages(ordersPage.getTotalPages());
        ordersDisplayDTOPage.setTotalElements(ordersPage.getTotalElements());
        return ordersDisplayDTOPage;
    }

    /**
     * create zip file of images and converted into byte array for the given order.
     * @param orderId id value of an order
     * @param zipFileSavingFileLocation Directory for save created zip file
     * @param zipFileNamePrefix prefix for zip file name for the order
     * @param zipFileNameSuffix suffix for zip file name for the order
     * @return byte array of zip file
     */
    @Override
    public byte[] createZipImageFileForTheOrder(long orderId,String zipFileSavingFileLocation,String zipFileNamePrefix,String zipFileNameSuffix) {
        byte [] zipBytes=null;

        Path orderImagesDirPath=Paths.get(OrdersConstants.IMAGE_FOLDER_PATH+"order_"+orderId+"/");
        if (Files.exists(orderImagesDirPath)){
            try(DirectoryStream<Path> imagePaths = Files.newDirectoryStream(orderImagesDirPath)) {
                List<File> paths = new ArrayList<>();
                for (Path image : imagePaths) {
                    if (Files.isRegularFile(image))
                        paths.add(image.toFile());
                }
                if (!paths.isEmpty()) {
                    Path zipFile = Paths.get(zipFileSavingFileLocation+zipFileNamePrefix + orderId + zipFileNameSuffix);
                    if (Files.exists(zipFile))
                        Files.delete(zipFile);
                    try (ZipFile zip=new ZipFile(zipFile.toString())){
                        zip.addFiles(paths);
                        zipBytes = FileUtils.readFileToByteArray(zipFile.toFile());
                        Files.delete(zipFile);
                    }
                }
            }
            catch (IOException e){
                log.info(e.getMessage());
            }
        }
        return zipBytes;
    }
}
