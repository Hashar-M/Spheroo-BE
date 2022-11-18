package com.qburst.spherooadmin.supplier;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * It carries the request informations that gives informations of the supplier to get delete.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SupplierDeleteDTO {
    private String supplierName;
}
