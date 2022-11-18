package com.qburst.spherooadmin.supplieruser;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

import static com.qburst.spherooadmin.constants.SupplierModelConstants.SUPPLIER_ID;
import static com.qburst.spherooadmin.constants.SupplierUserModelConstants.EMAIL_ID;
import static com.qburst.spherooadmin.constants.SupplierUserModelConstants.FIXED_MOBILE_NUMBER;
import static com.qburst.spherooadmin.constants.SupplierUserModelConstants.ID;
import static com.qburst.spherooadmin.constants.SupplierUserModelConstants.MOBILE_NUMBER;
import static com.qburst.spherooadmin.constants.SupplierUserModelConstants.NAME;
import static com.qburst.spherooadmin.constants.SupplierUserModelConstants.SUPPLIER_JOIN_COLUMN;
import static com.qburst.spherooadmin.constants.SupplierUserModelConstants.TABLE_NAME;
import static com.qburst.spherooadmin.constants.SupplierUserModelConstants.USER_TYPE;

/**
 * This class represent employees under a supplier{@link Supplier}
 */
@Entity
@Table(name = TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SupplierUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID)
    private long supplierUserId;

    @Column(name = NAME,nullable = false)
    private String name;

    @Column(name = MOBILE_NUMBER,nullable = false)
    private String mobileNumber;

    @Column(name = FIXED_MOBILE_NUMBER,nullable = false)
    private String fixedLineNumber;

    @Column(name = EMAIL_ID,nullable = false)
    private String supplierUserEmail;

    /**
     * {@code  @Enumerated(EnumType.ORDINAL) store enum type as a integer value.}
     */
    @Column(name = USER_TYPE,nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private SupplierUserType supplierUserType;
    /**
     * Many to one mapping with supplier {@link Supplier}
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = SUPPLIER_JOIN_COLUMN,
               referencedColumnName = SUPPLIER_ID)
    private Supplier supplier;
}
