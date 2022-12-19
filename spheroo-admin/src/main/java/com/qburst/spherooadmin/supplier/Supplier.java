package com.qburst.spherooadmin.supplier;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.qburst.spherooadmin.supplieruser.SupplierUser;
import lombok.AllArgsConstructor;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

import static com.qburst.spherooadmin.constants.SupplierModelConstants.CATEGORY_ID;
import static com.qburst.spherooadmin.constants.SupplierModelConstants.CATEGORY_NAME;
import static com.qburst.spherooadmin.constants.SupplierModelConstants.NAME;
import static com.qburst.spherooadmin.constants.SupplierModelConstants.RATING;
import static com.qburst.spherooadmin.constants.SupplierModelConstants.SUPPLIER_ID;
import static com.qburst.spherooadmin.constants.SupplierModelConstants.TABLE_NAME;

/**
 * model for supplier.
 */
@Entity
@Table(name = TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@ToString
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = SUPPLIER_ID)
    private long supplierId;

    @Column(name = NAME,nullable = false,unique = true)
    private String supplierName;

    @Min(value = 1,message = "rating should be greater than or equal one")
    @Max(value = 5,message = "rating should be less than or equal five")
    @Column(name = RATING)
    private int rating;

    /**
     * Each supplier class has an address, address is embedded in supplier, so it creates additional columns for address attributes in supplier table.
     * So no need to make {@link SupplierAddress} as an entity.
     */
    @Embedded
    private SupplierAddress supplierAddress;

    @Column(name = CATEGORY_ID,nullable = false)
    private long categoryId;

    @Column(name = CATEGORY_NAME,nullable = false)
    private String categoryNames;
    /**
     * for each supplier there is{@link SupplierUser} as members of the with different roles{@link com.qburst.spherooadmin.supplieruser.SupplierUserType} supplier.
     */

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "supplier",fetch = FetchType.EAGER)
    private List<SupplierUser> supplierUsers;

    /**
     * true value indicate the supplier is enabled and false value for disabled supplier.
     */
    @Column(name ="visibility",columnDefinition = "boolean default true",nullable = false)
    private boolean visibility=true;
  }
