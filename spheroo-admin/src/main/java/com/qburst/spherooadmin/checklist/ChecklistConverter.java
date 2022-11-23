package com.qburst.spherooadmin.checklist;

import com.qburst.spherooadmin.category.CategoryRepository;
import com.qburst.spherooadmin.service.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChecklistConverter {
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public CheclistPagingDTO converter(Checklist checklist){
        CheclistPagingDTO checlistPagingDTO=new CheclistPagingDTO();
        checlistPagingDTO.setId(checklist.getChecklistId());
        checlistPagingDTO.setName(checklist.getChecklistName());
        checlistPagingDTO.setDescription(checklist.getChecklistDescription());
        checlistPagingDTO.setServiceName(checklist.getService().getServiceName());
        long categoryId=serviceRepository.findCategoryIdFromServiceId(checklist.getService().getServiceId());
        String categoryName= categoryRepository.getCategoryNameFromCategoryId(categoryId);
        checlistPagingDTO.setCategoryName(categoryName);
        return checlistPagingDTO;
    }
}
