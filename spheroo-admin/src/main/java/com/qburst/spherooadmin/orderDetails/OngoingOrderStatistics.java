package com.qburst.spherooadmin.orderDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * class for storing ongoing order statistics.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OngoingOrderStatistics {
    private int OrdersCount;
    private int OrdersRedFlag;
    private int OrdersBlueFlag;
    private int OrdersGreenFlag;
}
