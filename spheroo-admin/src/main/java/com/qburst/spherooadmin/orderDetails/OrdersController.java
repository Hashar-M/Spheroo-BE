package com.qburst.spherooadmin.orderDetails;

import com.qburst.spherooadmin.exception.WrongDataForActionException;
import com.qburst.spherooadmin.search.OrderFilter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Controller for order entity
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    /**
     * Get an order details by providing its id
     * @param id order_id to retrieve from the database
     * @return Return the order serialized in JSON along with HTTP status OK and error message if not exist
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(ordersService.getOrderById(id));
    }

    /**
     * Get pageable list orders by different status OPEN, CLOSED, ESCALATION sorted by due date ASC and DSC.
     * @param page the page number to return.
     * @param noOfElements the number of elements to return to the page at a time.
     * @param columnToSort the column name tos sort the data.
     * @param isAsc describes sorting direction.
     * @param status filtering data on basis of status.
     * @return Return the order with filtered values serialized in JSON along with HTTP status OK and error message if not exist.
     */
    @GetMapping
    public ResponseEntity<?> findAllOrders(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "6") int noOfElements,
                                           @RequestParam(defaultValue = "deliveryToDate") String columnToSort, @RequestParam(defaultValue = "false") boolean isAsc, @RequestParam(defaultValue = "open") String status) {
        if(page<1){
            throw new WrongDataForActionException("page should not be less than 1");
        }
        if(noOfElements<1){
            throw new WrongDataForActionException("no of elements should be grater than 0");
        }
        if(status.equalsIgnoreCase("open") || status.equalsIgnoreCase("closed")||
                status.equalsIgnoreCase("escalations")||status.equalsIgnoreCase("overdue")) {
            return ResponseEntity.status(HttpStatus.OK).body(ordersService.getAllOrdersPaged(page-1,noOfElements,columnToSort,isAsc,status.toUpperCase()));
        } else {
            throw new WrongDataForActionException("Status value not in proper format");
        }
    }

    /**
     * function for getting orders statistics data.
     * @return returs order statistics data in the form of OrderStatisticsDTO class.
     */
    @GetMapping("/orders-statistics")
    public ResponseEntity<?> getOrdersStatistics(){
        return ResponseEntity.status(HttpStatus.OK).body(ordersService.getOrdersStatistics());
    }

    /**
     * function for getting orders details as CSV file
     * @param response
     * @param status status for filtering order data
     * @return return orders details as CSV file or error message with HTTP status.
     * @throws IOException
     */
    @GetMapping("/orders-export")
    public ResponseEntity<?>  exportOrdersToCSV(HttpServletResponse response,@RequestParam String status) throws IOException {
        if(status.equalsIgnoreCase("open") || status.equalsIgnoreCase("closed")||
                status.equalsIgnoreCase("escalations")||status.equalsIgnoreCase("overdue")) {
            response.setContentType("text/csv");
            String fileName= status+" order details.csv";
            String headerKey = "Content-Disposition";
            String headerValue ="attachment; filename="+fileName;
            response.setHeader(headerKey,headerValue);

            Page<OrdersDisplayDTO> ordersDisplayDTOPage =ordersService.getAllOrdersPaged(0,100,"deliveryToDate",false,status);
            ICsvBeanWriter csvBeanWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
            String[] csvHeader = {"order id","customer name","created date","delivery from_date","delivery to date",
                    "comments","zip code","order status","category name","service name","charge","assigned supplier"};
            String[] nameMapping= {"orderId","customerName","createdDate","deliveryFromDate","deliveryToDate","comments",
                    "zipCode","orderStatus","categoryName","serviceName","charge","assignedSupplier"};
            csvBeanWriter.writeHeader(csvHeader);
            for(OrdersDisplayDTO orderDisplay: ordersDisplayDTOPage){
                csvBeanWriter.write(orderDisplay,nameMapping);
            }
            csvBeanWriter.close();
            return ResponseEntity.status(HttpStatus.OK).body("success");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Status not in proper format");
        }
    }

    /**
     * API for download attached image with order.
     * @RequestParam orderId accepts order id.
     * @RequestParam index accepts image index.
     * @return return image for download.
     * @throws FileNotFoundException
     */
    @GetMapping("/download")
    public StreamingResponseBody downloadAttachedFile(HttpServletResponse response,@RequestParam long orderId,@RequestParam int index) throws FileNotFoundException {
        String fileName = "order_" + orderId + "_image" + index + ".jpg";
        response.setContentType("image/jpg");
        response.setHeader("Content-Disposition","attachment;filename="+fileName);
        String url =ordersService.getOrderById(orderId).getImagesList().get(index-1).getIssueImages();
        InputStream inputStream  = new FileInputStream(new File(url));
        return outputStream -> {
            int nRead;
            byte[] data = new byte[1024];
            while ((nRead = inputStream.read(data,0,data.length))!=-1){
                outputStream.write(data,0,nRead);
            }
        };
    }

    /**
     * API for assign order to supplier.
     * @param assignedOrder details of order and supplier.
     * @return Http status with message.
     */
    @PostMapping("/assign-order")
    public ResponseEntity<?> assignOrder(@Valid @RequestBody AssignedOrder assignedOrder){
        ordersService.assignOrder(assignedOrder);
        return ResponseEntity.status(HttpStatus.CREATED).body("Assigned successfully");
    }
    /**
     * add a new order by providing its id.
     * @param order the order data to add to the database.
     * @return Returns the HTTP status CREATED.
     */
    @PostMapping("/new-order")
    public ResponseEntity<HttpStatus> addOrder(@Valid @RequestBody Orders order) {
        ordersService.addOrder(order);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Update an existing order by providing its id.
     * @param amendOrderDTO the details to update to the specific order.
     * @return Returns the HTTP status OK/BAD_REQUEST with status message
     */
    @PutMapping("/amend-order")
    public ResponseEntity<?> updateOrder(@Valid @RequestBody AmendOrderDTO amendOrderDTO) {
        ordersService.updateOrdersById(amendOrderDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Amend completed");
    }

    /**
     * function for deleting order data by order id.
     * @param id accepts order id
     * @return message with HTTP status.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity <?> deleteOrder(@PathVariable long id){
        ordersService.deleteOrderById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("order deleted successfully");
    }

    /**
     * Allows you to search through orders based on the criteria defined in
     * the OrderFilter
     * @param orderFilter The criteria by which we search for the orders.
     * @param pageNo The page number of the list of orders.
     * @param noOfElements The number of elements to return at a time.
     * @return A Page of orders based on the provided criteria.
     */
    @GetMapping("/search")
    public ResponseEntity<Page<Orders>> findAllOrdersBySpecification(@RequestBody OrderFilter orderFilter, @RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "6") int noOfElements) {
        if(pageNo<1){
            throw new WrongDataForActionException("page should not be less than 1");
        }
        if(noOfElements<1){
            throw new WrongDataForActionException("no of elements should be grater than 0");
        }
        return new ResponseEntity<>(ordersService.findAllOrdersBySpecification(orderFilter, pageNo-1, noOfElements), HttpStatus.OK);
    }
}
