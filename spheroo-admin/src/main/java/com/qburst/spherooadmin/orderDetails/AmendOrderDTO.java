package com.qburst.spherooadmin.orderDetails;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;

import javax.validation.constraints.NotNull;
import java.util.Date;
@Getter
@Setter
public class AmendOrderDTO {
    @NotNull
    private long orderId;
    @NotNull
    private Date deliveryFromDate;
    @NotNull
    private Date deliveryToDate;
}
