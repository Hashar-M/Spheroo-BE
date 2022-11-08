package com.qburst.spherooadmin.orderDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="order_details")
public class OrderDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_id")
    private long orderId;

    @Column(name="customer_id")
    private long customerId;  //many to one mapping, customer table is not available

    @Column(name="category_id")
    private long categoryId;    //many to one mapping

    @Column(name="service_id")
    private long serviceId;     //many to one mapping

    @Column(name = "created_date")
    private Date createdDate;   //date format "2021-02-10"

    @Column(name="delivery_from_date")
    private Date deliveryFromDate;

    @Column(name="delivery_to_date")
    private Date deliveryToDate;

    @Column(name="comments")
    private String comments;

    @Column(name = "zip_code")
    private String zipCode; // from user table

    @Column(name = "order_status")
    private String orderStatus;

    @Lob
    @Column (name = "issue_attached_image")
    private String issuePicture;

    @Column(name = "service_priority")
    private String servicePriority;

}
