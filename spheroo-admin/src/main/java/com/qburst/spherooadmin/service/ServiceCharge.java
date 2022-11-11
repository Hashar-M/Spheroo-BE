package com.qburst.spherooadmin.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Getter
@Setter
// When hibernate fetches the data these fields are included in the json which can be ignored when we serialize it.
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "service_charge")
@AllArgsConstructor
@NoArgsConstructor
public class ServiceCharge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "charge_id")
    private long chargeId;

    @Column(name = "service_priority")
    private String servicePriority;

    @Column(name = "time_frame")
    private String timeFrame;

    @Column(name = "charge")
    private double charge;

}
