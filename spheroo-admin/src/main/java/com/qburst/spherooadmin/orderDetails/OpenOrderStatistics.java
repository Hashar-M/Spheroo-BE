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
    private int ordersCount;
    private int ordersUnassigned;
    private int ordersUnaccepted;
    private int ordersRedFlag;
    private int ordersBlueFlag;
    private int ordersGreenFlag;
}
