package com.qburst.spherooadmin.category;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class ManageCategoryDTO {
    private int page;
    private int noOfElements;
    private List<ManageCategoryDetails> manageCategoryDetailsList;
}
