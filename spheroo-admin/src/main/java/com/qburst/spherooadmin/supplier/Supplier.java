package com.qburst.spherooadmin.supplier;

import com.qburst.spherooadmin.supplieruser.SupplierUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "supplier")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
//@ToString
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplier_id")
    private long supplierId;

    @Column(name = "supplier_name",nullable = false,unique = true)
    private String supplierName;

    @Column(name = "supplier_tier")
    private int tier;

    @Column(name = "supplier_rating")
    private int rating;

    @Embedded
    private SupplierAddress supplierAddress;

    @Column(name = "category_id",nullable = false)
    private long categoryId;

    @Column(name = "category_name",nullable = false)
    private String categoryNames;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "supplier",fetch = FetchType.EAGER)
    private List<SupplierUser> supplierUsers;
  }
