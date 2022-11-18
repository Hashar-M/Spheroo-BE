package com.qburst.spherooadmin.supplier;

import com.qburst.spherooadmin.supplieruser.SupplierUsersAddDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @see SupplierAddressAddDTO,SupplierService
 * It carries all necessary informations to ADD a new supplier.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SupplierAddDTO {
    @Size(min = 6,max = 30,message = "size should be in range 6-30")
    private String supplierName;

    private int tier;

    private int rating;

    @NotNull
    private SupplierAddressAddDTO supplierAddressAddDTO;

    @Size(min = 1,max = 64)
    private String categoryNames;

    @NotNull
    private List<SupplierUsersAddDTO> supplierUsersAddDTOS;

}
