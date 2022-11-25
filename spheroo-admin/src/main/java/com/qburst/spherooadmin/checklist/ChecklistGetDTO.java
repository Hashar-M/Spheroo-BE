package com.qburst.spherooadmin.checklist;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChecklistGetDTO {
    private Checklist checklist;
    private String serviceName;
    private String categoryName;
}
