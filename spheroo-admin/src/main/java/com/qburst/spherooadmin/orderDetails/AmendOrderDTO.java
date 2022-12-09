package com.qburst.spherooadmin.orderDetails;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class AmendOrderDTO {
    @NotNull(message = "Invalid order id")
    private long orderId;
    @NotNull(message = "Invalid delivery from date")
    private Date deliveryFromDate;
    @NotNull(message = "Invalid delivery to date")
    private Date deliveryToDate;
}
