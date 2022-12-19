package com.qburst.spherooadmin.supplier;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FilterSupplierForAssignDTO {
    private int assignedTickets;
    private long supplierId;
    private String supplierName;
    private int rating;

    /**
     * this constructor is used by the jpa while jpa projection, for mapping the result set.
     * @param supplierId
     * @param supplierName
     */
    public FilterSupplierForAssignDTO(long supplierId,String supplierName,int rating){
        this.supplierId=supplierId;
        this.supplierName=supplierName;
        this.rating=rating;
    }
}
