package com.qburst.spherooadmin.supplier;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplierToAssignDTO {
    private long supplierId;
    private String supplierName;
    private String categoryName;
    private String serviceName;
    private int rating;
    private int assignedTickets;
}
