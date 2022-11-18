package com.qburst.spherooadmin.service;

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

/**
 * Represents Service charge entity.
 * it will list all service charges for each service.
 */
@Entity
@Getter
@Setter
// When hibernate fetches the data these fields are included in the json which can be ignored when we serialize it.
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "service_charge")
@AllArgsConstructor
@NoArgsConstructor
public class ServiceCharge {

    /**
     * The id for the ServiceCharge Entity
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "charge_id")
    private long chargeId;

    /**
     * The priority for the service, depending on the type, the charge can vary.
     */
    @Column(name = "service_priority")
    private String servicePriority;

    /**
     * The time frame for the service to occur.
     */
    @Column(name = "time_frame")
    private String timeFrame;

    /**
     * The charge for the service
     */
    @Column(name = "charge")
    private double charge;

}
