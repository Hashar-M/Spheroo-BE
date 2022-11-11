package com.qburst.spherooadmin.supplier;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "supplier")
public class Supplier {
    @Id
    private int supplierId;
    private String supplierName;
    @OneToOne
    private SupplierAddress address;
    @OneToOne
    private SupplierUser supplierUser;
    private int tier;
    private int rating;
}
