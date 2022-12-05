package com.qburst.spherooadmin.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ManageCategoryDetails {
    private long categoryId;
    private String categoryName;
    private long noOfServices;
    public ManageCategoryDetails(long categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }
}
