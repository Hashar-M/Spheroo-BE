package com.qburst.spherooadmin.supplier;

import com.qburst.spherooadmin.supplieruser.SuppliersUsersPostDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SupplierPostDTO {
    @Size(min = 6,max = 30,message = "size should be in range 6-30")
    private String supplierName;

    private int tier;

    private int rating;

    @NotNull
    private SupplierAddressPostDTO supplierAddressPostDTO;

    @Size(min = 1,max = 64)
    private String categoryNames;

    @NotNull
    private List<SuppliersUsersPostDTO> suppliersUsersPostDTOS;

}
