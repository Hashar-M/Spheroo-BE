package com.qburst.spherooadmin.checklist;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO contains data for adding a new checklist item.
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChecklistItemAddDTO {
    private String description;
}
