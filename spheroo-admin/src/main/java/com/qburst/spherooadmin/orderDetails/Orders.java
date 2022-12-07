package com.qburst.spherooadmin.orderDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
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
    @NotBlank(message = "invalid customer name")
    @Column(name="customer_name",nullable = false)
    private String customerName;
    /**
     * category_id field stores the id of selected category.
     */
    @NotBlank(message = "Invalid category id")
    @Column(name="category_id",nullable = false)
    private long categoryId;
    /**
     * service_id field stores the id of selected service.
     */
    @NotBlank(message = "Invalid service id")
    @Column(name="service_id",nullable = false)
    private long serviceId;
    /**
     * created_date field stores the order creation date.
     */
    @NotBlank(message = "Invalid created date")
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
    @NotBlank(message = "Invalid zip code")
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
    @Column(name = "order_status",nullable = false, columnDefinition = "varchar(255) default 'UNASSIGNED'")
    private String orderStatus;

    /**
     * Retrieves the name of the service from the service table.
     */
    @Formula("(SELECT Service.service_name FROM Service where Service.service_id = service_id)")
    private String serviceName;

    /**
     * it stores the list of image source path which is uploaded with order.
     */
    @OneToMany(targetEntity = IssueImages.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private List<IssueImages> imagesList;
    @Column(name = "is_amended", columnDefinition = "boolean default false")
    private boolean isAmended;
}
