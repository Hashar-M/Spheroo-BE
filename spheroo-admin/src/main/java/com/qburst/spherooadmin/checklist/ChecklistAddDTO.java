package com.qburst.spherooadmin.checklist;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ChecklistAddDTO {
    private String name;
    private String description;
    private String serviceName;
    private List<ChecklistItemAddDTO> checklistItems;
}
