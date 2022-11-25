package com.qburst.spherooadmin.orderDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

/**
 * Represents the orders entity
 * It will contain order details which are available at the order placing time.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name="orders")
public class Orders {
    /**
     * order_id field is the primary key for orders table
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_id")
    private long orderId;
    /**
     * customer_name  field stores the name of order issued customer.
     */
    @NotNull
    @Column(name="customer_name",nullable = false)
    private String customerName;
    /**
     * category_id field stores the id of selected category.
     */
    @NotNull
    @Column(name="category_id",nullable = false)
    private long categoryId;
    /**
     * service_id field stores the id of selected service.
     */
    @NotNull
    @Column(name="service_id",nullable = false)
    private long serviceId;
    /**
     * created_date field stores the order creation date.
     */
    @NotNull
    @Column(name = "created_date",nullable = false)
    private Date createdDate;
    /**
     * delivery_from_date stores the customer expecting servicing start date.
     */
    @Column(name="delivery_from_date")
    private Date deliveryFromDate;
    /**
     * delivery_to_date stores the customer side last date to service.
     */
    @Column(name="delivery_to_date")
    private Date deliveryToDate;
    /**
     * comments field stores the customers comments about order.
     */
    @Column(name="comments",length = 1024)
    private String comments;
    /**
     * Zipcode stores the zipcode of customer.
     */
    @NotNull
    @Column(name = "zip_code",nullable = false)
    private String zipCode;
    /**
     * order_status stores the current order status.
     * status may have unassigned, unaccepted, accepted, rejected, closed
     * open orders:
     *      unassigned,unaccepted
     * closed orders:
     *      accepted,rejected,closed
     */
    @NotNull
    @Column(name = "order_status",nullable = false)
    private String orderStatus;
    /**
     * it stores the list of image source path which is uploaded with order.
     */
    @OneToMany(targetEntity = IssueImages.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private List<IssueImages> imagesList;
}
