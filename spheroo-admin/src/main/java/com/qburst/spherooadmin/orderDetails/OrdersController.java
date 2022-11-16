package com.qburst.spherooadmin.orderDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    /*
    Controller for the Order entity
    Done:
    GET order by id
    GET pageable order by different status OPEN, CLOSED,ESCALATION sorted by due date ASC and DSC
    POST add new order
    PUT update the existing order

    TODO:
    API for download as CSV
    DELETE delete order by id
    upload picture if needed
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

        Orders orders = ordersService.getOrderById(id);
        if(orders != null) {
            return ResponseEntity.status(HttpStatus.OK).body(orders);
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
        log.info("page: "+page+"qty: "+noOfElements+" columnToSort: "+columnToSort+" isAsc: "+isAsc+" status: "+status);
        if(status.equalsIgnoreCase("open") || status.equalsIgnoreCase("closed")||
                status.equalsIgnoreCase("escalation")||status.equalsIgnoreCase("overdue")) {
            return ResponseEntity.status(HttpStatus.OK).body(ordersService.getAllOrdersPaged(page,noOfElements,columnToSort,isAsc,status));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Status not in proper format");
        }
    }

    @GetMapping("/orders-statistics")
    public ResponseEntity<?> getOrdersStatistics(){
        return ResponseEntity.status(HttpStatus.OK).body(ordersService.getOrdersStatistics());
    }

    /**
     * add a new order by providing its id.
     * @param order the order data to add to the database.
     * @return Returns the HTTP status CREATED.
     */
    @PostMapping("/new-order")
    public ResponseEntity<HttpStatus> addOrder(@RequestBody Orders order) {
        ordersService.addOrder(order);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Update an existing order by providing its id.
     * @param orders the category that we're updating the old category with.
     * @param id the category_id to retrieve from the database.
     * @return Returns the HTTP status OK/BAD_REQUEST with status message
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@RequestBody Orders orders, @PathVariable long id) {
        orders.setOrderId(id);
        boolean status =ordersService.updateOrdersById(orders);

        if(!status) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("order not available");
        }
        return ResponseEntity.status(HttpStatus.OK).body("order updated");
    }
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
