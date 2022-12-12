package com.qburst.spherooadmin.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * DTO is used to carrying data necessary for update a {@link Service}
 * @see ServiceChargePutDTO
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServicePutDTO {
    @Positive(message = "service id should be a positive value")
    @NotNull(message = "service id can't be null")
    private long serviceId;
    @NotBlank(message = "service name can not be blank")
    private String serviceName;
    private String description;
    private Boolean variablePrice;
    @Valid
    private List<ServiceChargePutDTO> serviceChargeList;
}
