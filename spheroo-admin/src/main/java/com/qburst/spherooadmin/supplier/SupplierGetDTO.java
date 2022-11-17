package com.qburst.spherooadmin.supplier;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SupplierGetDTO {
    private long supplierId;
    private String supplierName;
    private String contactName;
    private String contactNumber;
    private String emailId;
    private String category;
    private int pinCode;
}
