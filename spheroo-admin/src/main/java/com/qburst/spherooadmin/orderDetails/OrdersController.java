package com.qburst.spherooadmin.orderDetails;

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
import javax.validation.Valid;
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

        OrdersDisplayDTO ordersDisplayDTO = ordersService.getOrderById(id);
        if(ordersDisplayDTO  != null) {
            return ResponseEntity.status(HttpStatus.OK).body(ordersDisplayDTO );
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("order not available");
        }
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
    public ResponseEntity<?> findAllOrders(@RequestParam int page, @RequestParam int noOfElements,
                                           @RequestParam String columnToSort, @RequestParam boolean isAsc, @RequestParam String status) {
        if(status.equalsIgnoreCase("open") || status.equalsIgnoreCase("closed")||
                status.equalsIgnoreCase("escalation")||status.equalsIgnoreCase("overdue")) {
            return ResponseEntity.status(HttpStatus.OK).body(ordersService.getAllOrdersPaged(page,noOfElements,columnToSort,isAsc,status.toUpperCase()));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Status not in proper format");
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
                status.equalsIgnoreCase("escalation")||status.equalsIgnoreCase("overdue")) {
            response.setContentType("text/csv");
            String fileName= status+" order details.csv";
            String headerKey = "Content-Disposition";
            String headerValue ="attachment; filename="+fileName;
            response.setHeader(headerKey,headerValue);

            Page<OrdersDisplayDTO> ordersDisplayDTOPage =ordersService.getAllOrdersPaged(0,100,"delivery_to_date",false,status);
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
    @GetMapping("/download/{orderId}")
    public StreamingResponseBody downloadAttachedFile(HttpServletResponse response,@PathVariable long orderId) throws FileNotFoundException {
        String fileName = "order#"+String.valueOf(orderId)+"image.jpeg";
        response.setContentType("image/jpg");
        response.setHeader("Content-Disposition","attachment;filename="+fileName);
        String url =ordersService.getOrderById(orderId).getIssueImagesList().get(0).getIssueImages();
//        String url ="/home/hashar/Pictures/Screenshots/Screenshot from 2022-11-22 11-40-14.png";
        InputStream inputStream  = new FileInputStream(new File(url));
        return outputStream -> {
            int nRead;
            byte[] data = new byte[1024];
            while ((nRead = inputStream.read(data,0,data.length))!=-1){
                outputStream.write(data,0,nRead);
            }
        };
    }
//    @GetMapping("/getsuppliers-byorder")
//    public ResponseEntity<?> getSupplierByCategoryIdAndZip(@RequestParam long categoryId, @RequestParam String Zipcode){
//
//    }
    @PostMapping("/assign-order")
    public ResponseEntity<?> assignOrder(@Valid @RequestBody AssignedOrder assignedOrder){
        if(ordersService.assignOrder(assignedOrder)){
            return ResponseEntity.status(HttpStatus.OK).body("saved successfully");
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("order id or supplier id not exist");
        }
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
        boolean status =ordersService.updateOrdersById(amendOrderDTO);
        if(!status) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("order not available");
        }
        return ResponseEntity.status(HttpStatus.OK).body("order updated");
    }

    /**
     * function for deleting order data by order id.
     * @param id accepts order id
     * @return message with HTTP status.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity <?> deleteOrder(@PathVariable long id){
        boolean deleteStatus = ordersService.deleteOrderById(id);
        if(deleteStatus){
            return ResponseEntity.status(HttpStatus.OK).body("order deleted successfully");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no order details with this Id");
        }
    }
}
