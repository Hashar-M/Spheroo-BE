package com.qburst.spherooadmin.checklist;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * It possesses data of a {@link Checklist} in the pagination for checklist.
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CheclistPagingDTO {
    private Long id;
    private String name;
    private String description;
    private String categoryName;
    private String serviceName;
}
