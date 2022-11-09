package com.qburst.spherooadmin.orderDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name="orders")
public class Orders
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_id")
    private long orderId;

    @Column(name="customer_id")  //need to add not null
    private long customerId;  //many to one mapping, customer table is not available

    @Column(name="category_id")  //not null
    private long categoryId;    //many to one mapping

    @Column(name="service_id")  //not null
    private long serviceId;     //many to one mapping

    @Column(name = "created_date")
    private Date createdDate;   //date format

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

    @Column (name = "issue_attached_image")
    private String issuePicture;

    @Column(name = "service_priority")
    private String servicePriority;

}
