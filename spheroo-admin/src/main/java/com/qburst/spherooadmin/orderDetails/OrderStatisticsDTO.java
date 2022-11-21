package com.qburst.spherooadmin.orderDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * model for storing order statistics data.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatisticsDTO {
    private int openOrdersCount;
    private int openOrdersUnassigned;
    private int openOrdersUnaccepted;
    private int openOrdersRedFlag;
    private int openOrdersBlueFlag;
    private int openOrdersGreenFlag;

    private int ongoingOrdersCount;
    private int ongoingOrdersRedFlag;
    private int ongoingOrdersBlueFlag;
    private int ongoingOrdersGreenFlag;

    private int overdueOrdersCount;
    private int overdueOrdersUnassigned;
    private int overdueOrdersUnaccepted;

    private int escalationsCount;
}
