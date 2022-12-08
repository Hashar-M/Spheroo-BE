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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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

    /**
     * The id of the service entity
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long serviceId;

    /**
     * Name of the service
     */
    @NotBlank(message = "service name can't be blank or null")
    @Size(max = 64,message = "allowed length for service name is 64")
    @Column(name = "service_name", length = 64, nullable = false, unique = true)
    private String serviceName;

    /**
     * Description of the service
     */
    @Size(max = 1024,message = "allowed length for description is 1024")
    @Column(name = "description", length = 1024)
    private String description;

    /**
     * Variable price field, when set to true, that indicates that the price for the service
     * can vary from the initial charge shown to the user.
     */
    @Column(name="variable_price")
    private Boolean variablePrice;

    /**
     * Each service can have a set of different charges depending on various different factors
     */
    @OneToMany(targetEntity = ServiceCharge.class,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    private List<ServiceCharge>  serviceChargeList;
}
