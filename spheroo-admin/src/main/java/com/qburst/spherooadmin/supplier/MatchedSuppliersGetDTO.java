package com.qburst.spherooadmin.supplier;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include. NON_NULL)
public class MatchedSuppliersGetDTO {
    private List<SupplierToAssignDTO> supplierToAssignDTOList;
    private List<FilterSupplierForAssignDTO> filterSupplierForAssignDTOList;
}
