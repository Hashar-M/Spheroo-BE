package com.qburst.spherooadmin.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * DTO is used to carrying data necessary for update a {@link Service}
 * This DTO carries data for {@link ServiceCharge}
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceChargePutDTO {
    @Positive(message = "service charge id should be positive value")
    @NotNull(message = "service charge id can not be empty")
    private long chargeId;
    private String servicePriority;
    private String timeFrame;
    private double charge;
}
