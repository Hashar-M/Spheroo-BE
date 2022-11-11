package com.qburst.spherooadmin.supplier;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "")
public class SupplierUser {
    @Id
    private int supplierUserId;
    private String name;
    private int mobileNumber;
    private int fixedLineNumber;
    private String supplierUserEmail;
    @OneToOne
    private Supplier supplier;
}
