package com.qburst.spherooadmin.supplier;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SupplierAddressPostDTO {
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
    //@Size(max = 255)
    private int pinCode;

    @NotNull
   // @Size(max = 255)
    private long buildNo;
}
