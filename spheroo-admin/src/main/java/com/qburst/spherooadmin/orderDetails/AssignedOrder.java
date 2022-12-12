package com.qburst.spherooadmin.orderDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * Represents assigned order entity.
 * it will contain all details related to order assign process.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name="assigned_order")
public class AssignedOrder {

    /**
     * this field represents assign id, primary key for assigned_order.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="assign_id")
    private long assignId;
    /**
     * order_id field stores the id of assigned order.
     */
//    @NotBlank(message = "Invalid order id")
    @Column(name="order_id",nullable = false,unique = true)
    private long orderId;
    /**
     * supplier_id field stores the id of assigned supplier.
     */
//    @NotBlank(message = "Invalid supplier id")
    @Column(name="supplier_id",nullable = false)
    private long supplierId;
    /**
     * assigned_date field stores the assignation completed date.
     */
//    @NotBlank(message = "Invalid assigned date")
    @Column(name = "assigned_date",nullable = false)
    private Date assignedDate;
}
