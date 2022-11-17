package com.qburst.spherooadmin.supplieruser;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.qburst.spherooadmin.supplier.Supplier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "supplier_user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
//@ToString
public class SupplierUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplierUser_id")
    private long supplierUserId;

    @Column(name = "supplier_user_name",nullable = false)
    private String name;

    @Column(name = "supplier_user_mob_no",nullable = false)
    private String mobileNumber;

    @Column(name = "supplier_user_fixed_mob_no",nullable = false)
    private String fixedLineNumber;

    @Column(name = "supplier_user_email",nullable = false)
    private String supplierUserEmail;

    @Column(name = "supplier_user_roles",nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private SupplierUserType supplierUserType;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "supplier_id",
               referencedColumnName = "supplier_id")
    private Supplier supplier;
}
