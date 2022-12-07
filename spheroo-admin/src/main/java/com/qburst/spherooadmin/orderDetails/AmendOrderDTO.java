package com.qburst.spherooadmin.orderDetails;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
public class AmendOrderDTO {
    @NotBlank(message = "Invalid order id")
    private long orderId;
    @NotBlank(message = "Invalid delivery from date")
    private Date deliveryFromDate;
    @NotBlank(message = "Invalid delivery to date")
    private Date deliveryToDate;
}
