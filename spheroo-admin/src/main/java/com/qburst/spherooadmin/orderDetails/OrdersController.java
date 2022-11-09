package com.qburst.spherooadmin.orderDetails;

import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/orders")
public class OrdersController
{
    /*
    get by id
    get all
    get orders by page
    grt orders by Escalation
    get orders sort by date due asc & desc
     */

    @Autowired
    private OrdersService ordersService;

    @GetMapping("/id={id}")
    public ResponseEntity<Orders> getOrderById(@PathVariable long id)
    {
        return new ResponseEntity<>(ordersService.getOrderById(id), HttpStatus.OK);
    }

    @PostMapping("/new-order")
    public ResponseEntity<HttpStatus> addOrder(@RequestBody Orders order)
    {
        ordersService.addOrder(order);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping("/page={page}&=qty={noOfElements}&=columnToSort={columnToSort}&=isAsc={isAsc}")
    public ResponseEntity<Page<Orders>> findAllOrders(@PathVariable int page,@PathVariable int noOfElements,
                                                      @PathVariable String columnToSort,@PathVariable boolean isAsc)
    {
        return new ResponseEntity<>(ordersService.getAllOrdersPaged(page,noOfElements,columnToSort,isAsc),HttpStatus.OK);
    }

    @PutMapping("/id={id}")
    public ResponseEntity<HttpStatus> updateOrder(@RequestBody Orders orders, @PathVariable long id)
    {
        orders.setOrderId(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
