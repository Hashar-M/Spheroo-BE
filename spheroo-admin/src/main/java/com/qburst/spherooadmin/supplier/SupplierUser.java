package com.qburst.spherooadmin.supplier;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class SupplierUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplierUser_id")
    private int supplierUserId;

    @Column(name = "supplier_user_name",nullable = false)
    private String name;

    @Column(name = "supplier_user_mob_no",nullable = false)
    private long mobileNumber;

    @Column(name = "supplier_user_fixed_mob_no",nullable = false)
    private long fixedLineNumber;

    @Column(name = "supplier_user_email",nullable = false)
    private String supplierUserEmail;

    @Column(name = "supplier_user_roles",nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private SupplierUserType supplierUserType;

    @ManyToOne(optional = false,fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "supplier_id",
               referencedColumnName = "supplier_id")
    private Supplier supplier;
}
