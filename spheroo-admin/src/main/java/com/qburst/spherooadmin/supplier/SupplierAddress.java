package com.qburst.spherooadmin.supplier;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import static com.qburst.spherooadmin.constants.SupplierAddressModelConstants.*;

/**
 * This class is used to represent the address of supplier
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
/**
 * Attributes of {@link SupplierAddress} is specified for make it include in supplier table as a field.
 */
@AttributeOverrides({@AttributeOverride(name = "country",
                    column = @Column(name = COUNTRY,nullable = false)),
                    @AttributeOverride(name = "town",
                    column = @Column(name = TOWN,nullable = false)),
                    @AttributeOverride(name = "district",
                    column = @Column(name = DISTRICT,nullable = false)),
                    @AttributeOverride(name = "pinCode",
                    column = @Column(name = PINCODE,nullable = false,unique = true)),
                    @AttributeOverride(name = "buildNo",
                    column =@Column(name = BUILD_NO,nullable = false))})
public class SupplierAddress {
    private String country;
    private String town;
    private String district;
    private int pinCode;
    private long buildNo;
}
