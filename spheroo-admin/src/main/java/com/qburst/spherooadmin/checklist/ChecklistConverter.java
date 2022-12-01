package com.qburst.spherooadmin.checklist;

import com.qburst.spherooadmin.category.CategoryRepository;
import com.qburst.spherooadmin.service.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This class contains methods that process a checklist data.
 */
@Component
public class ChecklistConverter {
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * method that converts {@link Checklist} into {@link ChecklistPagingDTO}.
     *  @param checklist
     * @return {@link ChecklistPagingDTO}
     */

    public ChecklistPagingDTO converter(Checklist checklist){
        ChecklistPagingDTO checklistPagingDTO =new ChecklistPagingDTO();
        checklistPagingDTO.setId(checklist.getChecklistId());
        checklistPagingDTO.setName(checklist.getChecklistName());
        checklistPagingDTO.setDescription(checklist.getChecklistDescription());
        checklistPagingDTO.setServiceName(checklist.getService().getServiceName());

        long categoryId=serviceRepository.findCategoryIdFromServiceId(checklist.getService().getServiceId());

        String categoryName= categoryRepository.getCategoryNameFromCategoryId(categoryId);
        checklistPagingDTO.setCategoryName(categoryName);
        return checklistPagingDTO;
    }
}
