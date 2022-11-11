package com.qburst.spherooadmin.orderDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Represents the orders entity
 * It will contain order details which are available at the order placing time.
 *
 */

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

    @NotNull
    @Column(name="customer_name",nullable = false)
    private String customerName;  //many to one mapping, customer table is not available

    @NotNull
    @Column(name="category_id",nullable = false)  //not null
    private long categoryId;    //many to one mapping

    @NotNull
    @Column(name="service_id",nullable = false)  //not null
    private long serviceId;     //many to one mapping

    @NotNull
    @Column(name = "created_date",nullable = false)
    private Date createdDate;   //date format

    @Column(name="delivery_from_date")
    private Date deliveryFromDate;

    @Column(name="delivery_to_date")
    private Date deliveryToDate;

    @Column(name="comments",length = 1024)
    private String comments;

    @NotNull
    @Column(name = "zip_code",nullable = false)
    private String zipCode; // from user table

    /** status may have unassigned, unaccepted, accepted, rejected, closed
     * open orders:
     *      unassigned,unaccepted
     * closed orders:
     *      accepted,rejected,closed
     */
    @NotNull
    @Column(name = "order_status",nullable = false)
    private String orderStatus;

    @Column (name = "issue_attached_image")
    private String issuePicture;


}
