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

    private OpenOrderStatistics open;
    private OngoingOrderStatistics ongoing;
    private OverdueOrderStatistics Overdue;
    private int escalationsCount;
}
