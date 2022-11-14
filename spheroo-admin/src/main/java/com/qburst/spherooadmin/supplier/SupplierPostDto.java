package com.qburst.spherooadmin.supplier;

import com.qburst.spherooadmin.category.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SupplierPostDto {
    private String supplierName;
    private int tier;
    private int rating;
    private Category category;
    private List<SupplierUser> supplierUsers;
    private SupplierAddress supplierAddress;
   /* private String country;
    private String town;
    private String district;
    private String pinCode;
    private String buildNo;*/
}
