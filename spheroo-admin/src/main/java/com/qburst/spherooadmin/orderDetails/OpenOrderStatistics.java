package com.qburst.spherooadmin.orderDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * class for storing open order statistics.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OpenOrderStatistics {
    private int OrdersCount;
    private int OrdersUnassigned;
    private int OrdersUnaccepted;
    private int OrdersRedFlag;
    private int OrdersBlueFlag;
    private int OrdersGreenFlag;
}
