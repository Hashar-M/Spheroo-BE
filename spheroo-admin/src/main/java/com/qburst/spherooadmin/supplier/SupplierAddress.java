package com.qburst.spherooadmin.supplier;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "")
public class SupplierAddress {
    @Id
    private int addressId;
    private String country;
    private String town;
    private String district;
    private String pinCode;
    private String buildNo;
    @OneToOne
    private Supplier supplier;
}
