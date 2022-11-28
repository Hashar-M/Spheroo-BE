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
    private int ordersCount;
    private int ordersUnassigned;
    private int ordersUnaccepted;
}
