package com.qburst.spherooadmin.orderDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.List;

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
    private long categoryId;
    private long serviceId;
    private String categoryName;
    private String serviceName;
    private double charge;
    private String assignedSupplier;
    private List<IssueImages> imagesList;
    private boolean isAmended;

    public OrdersDisplayDTO(long orderId, String customerName, Date createdDate, Date deliveryFromDate, Date deliveryToDate, String comments, String zipCode, String orderStatus, long categoryId,long serviceId, String categoryName, String serviceName, boolean isAmended) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.createdDate = createdDate;
        this.deliveryFromDate = deliveryFromDate;
        this.deliveryToDate = deliveryToDate;
        this.comments = comments;
        this.zipCode = zipCode;
        this.orderStatus = orderStatus;
        this.categoryId = categoryId;
        this.serviceId =serviceId;
        this.categoryName = categoryName;
        this.serviceName = serviceName;
        this.isAmended = isAmended;
    }
}
