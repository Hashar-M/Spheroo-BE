package com.qburst.spherooadmin.orderDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * class for storing overdue order statistics.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OverdueOrderStatistics {
    private int OrdersCount;
    private int OrdersUnassigned;
    private int OrdersUnaccepted;
}
