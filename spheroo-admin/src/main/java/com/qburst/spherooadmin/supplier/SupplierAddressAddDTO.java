package com.qburst.spherooadmin.supplier;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * It carries informations of {@link SupplierAddress} and associate with {@link SupplierAddDTO} for adding a new supplier.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SupplierAddressAddDTO {
    @NotNull
    @NotEmpty
    @Size(max = 48)
    private String country;

    @NotNull
    @NotEmpty
    @Size(max = 255)
    private String town;

    @NotNull
    @NotEmpty
    @Size(max = 255)
    private String district;

    @NotNull
    private int pinCode;

    @NotNull
    private long buildNo;
}
