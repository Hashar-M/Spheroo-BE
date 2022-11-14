package com.qburst.spherooadmin.supplier;

import com.qburst.spherooadmin.category.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@ToString(/*exclude = "category"*/)
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

    @ManyToOne(optional = false,cascade = CascadeType.ALL,
              fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id",
                referencedColumnName = "category_id")
    private Category category;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "supplier",fetch = FetchType.EAGER)
    private List<SupplierUser> supplierUsers;
}
