package com.qburst.spherooadmin.supplier;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SupplierPageDTO {
    private List<SupplierGetDTO> supplierList;
    private int totalPages;
    private int pageSize;
    private int pageNumber;
}
