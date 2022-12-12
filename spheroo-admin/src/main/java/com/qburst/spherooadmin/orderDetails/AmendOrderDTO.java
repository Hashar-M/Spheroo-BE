package com.qburst.spherooadmin.orderDetails;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class AmendOrderDTO {
    @NonNull
    private long orderId;
    @NotNull(message = "from date cannot be null")
    private Date deliveryFromDate;
    @NotNull(message = "To date cannot be null")
    private Date deliveryToDate;
}
