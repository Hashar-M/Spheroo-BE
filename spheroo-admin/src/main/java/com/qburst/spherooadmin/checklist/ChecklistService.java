package com.qburst.spherooadmin.checklist;

import com.qburst.spherooadmin.signup.ResponseDTO;
import org.springframework.data.domain.Page;

/**
 * interface have abstract methods for service layer for {@link Checklist} and {@link ChecklistItem}.
 */
public interface ChecklistService {
    ResponseDTO addChecklist(ChecklistAddDTO checklistAddDTO);
    Page<ChecklistPagingDTO> pageChecklist(int pageNumber, int pageSize);
    ResponseDTO deleteChecklistAndChecklistItemFromId(Long id);
    ChecklistGetDTO getChecklistById(Long id);
    ResponseDTO updateTheChecklist(Checklist checklist,String serviceName);
    Page<ChecklistPagingDTO> findChecklistByName(String checklistName, int pageNumber, int pageSize);
}
