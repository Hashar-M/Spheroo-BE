package com.qburst.spherooadmin.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.qburst.spherooadmin.category.Category;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * Represent Service Entity
 * it will list all services under each category
 */
@Entity
@Getter
@Setter
// When hibernate fetches the data these fields are included in the json which can be ignored when we serialize it.
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "service")
@AllArgsConstructor
@NoArgsConstructor
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long serviceId;

    @Column(name = "service_name", length = 64, nullable = false, unique = true)
    private String serviceName;

    @Column(name = "description", length = 1024)
    private String description;

    @Column(name="variable_price")
    private String variablePrice;


    @OneToMany(targetEntity = ServiceCharge.class,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    private List<ServiceCharge>  serviceChargeList;
}
