package com.qburst.spherooadmin.supplier;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
/**
 * It stores a list of supplier, used in GET request for supplier as a page.
 */
public class SupplierPagingDTO {
    private List<SupplierGetDTO> supplierGetDTO;
}
