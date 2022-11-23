package com.qburst.spherooadmin.checklist;

import com.qburst.spherooadmin.service.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ChecklistConverter {
    @Autowired
    private static ServiceRepository serviceRepository;
    public static CheclistPagingDTO converter(Checklist checklist){
        CheclistPagingDTO checlistPagingDTO=new CheclistPagingDTO();
        checlistPagingDTO.setId(checklist.getChecklistId());
        checlistPagingDTO.setName(checklist.getChecklistName());
        checlistPagingDTO.setDescription(checklist.getChecklistDescription());
        checlistPagingDTO.setServiceName(checklist.getService().getServiceName());
       // checlistPagingDTO.setCategoryName(serviceRepository.findCategoryIdFromServiceId(checklist.getService().getServiceId()));
        return checlistPagingDTO;
    }
}
