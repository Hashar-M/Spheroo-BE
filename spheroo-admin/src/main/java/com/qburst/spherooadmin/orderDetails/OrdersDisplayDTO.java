package com.qburst.spherooadmin.orderDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrdersDisplayDTO {
    private long orderId;
    private String customerName;
    private Date createdDate;
    private Date deliveryFromDate;
    private Date deliveryToDate;
    private String comments;
    private String zipCode;
    private String orderStatus;
    private String categoryName;
    private String serviceName;
    private double charge;
    private String assignedSupplier;
}
