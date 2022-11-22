package com.qburst.spherooadmin.orderDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_id")
    private long orderId;

    @NotNull
    @Column(name="customer_name",nullable = false)
    private String customerName;

    @NotNull
    @Column(name="category_id",nullable = false)
    private long categoryId;

    @NotNull
    @Column(name="service_id",nullable = false)
    private long serviceId;

    @NotNull
    @Column(name = "created_date",nullable = false)
    private Date createdDate;

    @Column(name="delivery_from_date")
    private Date deliveryFromDate;

    @Column(name="delivery_to_date")
    private Date deliveryToDate;

    @Column(name="comments",length = 1024)
    private String comments;

    @NotNull
    @Column(name = "zip_code",nullable = false)
    private String zipCode;

    /** status may have unassigned, unaccepted, accepted, rejected, closed
     * open orders:
     *      unassigned,unaccepted
     * closed orders:
     *      accepted,rejected,closed
     */
    @NotNull
    @Column(name = "order_status",nullable = false)
    private String orderStatus;

    @OneToMany(targetEntity = IssueImages.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private List<IssueImages> imagesList;
}
